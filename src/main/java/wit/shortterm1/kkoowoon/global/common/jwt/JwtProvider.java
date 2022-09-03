package wit.shortterm1.kkoowoon.global.common.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import wit.shortterm1.kkoowoon.domain.user.exception.JwtTokenException;
import wit.shortterm1.kkoowoon.domain.user.service.CustomAccountDetailService;
import wit.shortterm1.kkoowoon.global.common.redis.RedisService;
import wit.shortterm1.kkoowoon.global.error.exception.EmptyTokenException;
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
    private final long ACCESS_TOKEN_EXPIRE_TIME = 3000 * 60 * 1000L;              // 30 * 100분
    private final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // 7일
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
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }


    private Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = customAccountDetailService.loadUserByUsername(getKakaoId(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getKakaoId(String accessToken) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody().getSubject();
    }

    public String getKakaoId(HttpServletRequest request) {
        String accessToken = extractAccessToken(request.getHeader("Authorization"));
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody().getSubject();
    }

    private String extractAccessToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.equals("")) {
            throw new EmptyTokenException(ErrorCode.EMPTY_TOKEN_ERROR);
        }
        return authorizationHeader.split(" ")[1];
    }

    public String createAccessToken(String kakaoId, String roles) {
        long tokenInvalidTime = ACCESS_TOKEN_EXPIRE_TIME;
        return createToken(kakaoId, roles, tokenInvalidTime);
    }

    public String createRefreshToken(String kakaoId, String roles) {
        long tokenInvalidTime = REFRESH_TOKEN_EXPIRE_TIME;
        String refreshToken = createToken(kakaoId, roles, tokenInvalidTime);
        redisService.setValues(kakaoId, refreshToken, Duration.ofSeconds(tokenInvalidTime));
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
            throw new JwtTokenException(ErrorCode.NO_SUCH_TOKEN);
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
                throw new JwtTokenException(ErrorCode.TOKEN_EXPIRED);
            }
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return getAuthentication(token);
        } catch (MalformedJwtException  | SignatureException | UnsupportedJwtException e) {
            throw new JwtTokenException(ErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            request.setAttribute(exception, "JWT compact of handler are invalid");
        }
        return null;
    }

}
