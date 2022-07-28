package wit.shortterm1.kkoowoon.global.common.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import wit.shortterm1.kkoowoon.domain.exception.JwtTokenException;
import wit.shortterm1.kkoowoon.domain.service.CustomAccountDetailService;
import wit.shortterm1.kkoowoon.global.common.redis.RedisService;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;
    private static final Long TOKEN_VALID_TIME = 1000L * 60 * 30; // 3m
    private final CustomAccountDetailService customAccountDetailService;
    private final RedisService redisService;
    @Value("${spring.jwt.blacklist.access-token}")
    private String blackListATPrefix;

    // 의존성 주입 후, 초기화를 수행
    // 객체 초기화, secretKey Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String createToken(String nickname, String roles){
        Claims claims = Jwts.claims().setSubject(nickname); // claims 생성 및 payload 설정
        claims.put("roles", roles); // 권한 설정, key/ value 쌍으로 저장
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + TOKEN_VALID_TIME)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }



    private Authentication getAuthentication(String token) {
        UserDetails userDetails = customAccountDetailService.loadUserByUsername(getKakaoId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getKakaoId(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public String createAccessToken(String kakaoId, String roles) {
        Long tokenInvalidTime = TOKEN_VALID_TIME;
        return this.createToken(kakaoId, roles, tokenInvalidTime);
    }

    public String createRefreshToken(String kakaoId, String roles) {
        Long tokenInvalidTime = TOKEN_VALID_TIME;
        String refreshToken = this.createToken(kakaoId, roles, tokenInvalidTime);
        redisService.setValues(kakaoId, refreshToken, Duration.ofMillis(tokenInvalidTime));
        return refreshToken;
    }

    private String createToken(String kakaoId, String roles, Long tokenInvalidTime){
        Claims claims = Jwts.claims().setSubject(kakaoId); // claims 생성 및 payload 설정
        claims.put("roles", roles); // 권한 설정, key/ value 쌍으로 저장
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenInvalidTime)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }

    public void checkRefreshToken(String kakaoId, String refreshToken) {
        String redisRT = redisService.getValues(kakaoId);
        if (!refreshToken.equals(redisRT)) {
            throw new JwtTokenException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    public void logout(String accessToken) {
        Authentication authentication = getAuthentication(accessToken);
        long expiredAccessTokenTime = getExpiredTime(accessToken).getTime() - new Date().getTime();
        redisService.setValues(blackListATPrefix + accessToken, authentication.getName(), Duration.ofMillis(expiredAccessTokenTime));
        redisService.deleteValues(authentication.getName()); // Delete RefreshToken In Redis
    }

    private Date getExpiredTime(String accessToken) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody().getExpiration();
        } catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new JwtTokenException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Authentication validateToken(HttpServletRequest request, String token) {
        String exception = "exception";
        try {
            String expiredAT = redisService.getValues(blackListATPrefix + token);
            if (expiredAT != null) {
                throw new ExpiredJwtException(null, null, null);
            }
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return getAuthentication(token);
        } catch (MalformedJwtException  | SignatureException | UnsupportedJwtException e) {
            request.setAttribute(exception, "토큰의 형식을 확인하세요");
        } catch (ExpiredJwtException e) {
            request.setAttribute(exception, "토큰이 만료되었습니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute(exception, "JWT compact of handler are invalid");
        }
        return null;
    }

}
