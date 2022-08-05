package wit.shortterm1.kkoowoon.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.user.dto.response.NicknameDuplicateDto;
import wit.shortterm1.kkoowoon.domain.user.dto.response.UserInfoResponseDto;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.dto.request.SignUpRequestDto;
import wit.shortterm1.kkoowoon.domain.user.dto.response.LoginResponseDto;
import wit.shortterm1.kkoowoon.domain.user.dto.response.TempResponse;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.exception.UserDuplicateException;
import wit.shortterm1.kkoowoon.domain.user.exception.WrongPasswordException;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.global.common.jwt.JwtProvider;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final OauthService oauthService;

    public LoginResponseDto newLogin(String code) {
        String kakaoId = oauthService.findKakaoUser(oauthService.getKakaoAccessToken(code));
        Account account = accountRepository.findByKakaoId(kakaoId).orElse(null);
        if (account == null) {
            return LoginResponseDto.createDto(kakaoId);
        } else {
            String accessToken = jwtProvider.createAccessToken(account.getKakaoId(), account.getRole());
            String refreshToken = jwtProvider.createRefreshToken(account.getKakaoId(), account.getRole());
            return LoginResponseDto.createDto(accessToken, refreshToken);
        }
    }

    @Transactional
    public TempResponse signUp(SignUpRequestDto signUpReqDto) {
//        String kakaoId = oauthService.findKakaoUser(signUpReqDto.getKakaoId());
        checkKakaoIdIsDuplicate(signUpReqDto.getKakaoId());
//        signUpReqDto.setKakaoId(kakaoId);
        Account newAccount = signUpReqDto.toEntity();
        accountRepository.save(newAccount);
        return new TempResponse("회원가입 성공", HttpStatus.CREATED);
    }

    private void checkKakaoIdIsDuplicate(String kakaoId) {
        boolean isDuplicate = accountRepository.existsByKakaoId(kakaoId);
        if(isDuplicate) {
            throw new UserDuplicateException(ErrorCode.USER_ALREADY_EXIST);
        }
    }

    private void checkPasswordConvention(String password) {
        // TODO: Check Password Convention
    }

    public LoginResponseDto login(String kakaoId) {
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
//        checkPassword(password, account.getPassword());
        String accessToken = jwtProvider.createAccessToken(account.getKakaoId(), account.getRole());
        String refreshToken = jwtProvider.createRefreshToken(account.getKakaoId(), account.getRole());
        return LoginResponseDto.createDto(accessToken, refreshToken);
    }

    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if(!isSame) {
            throw new WrongPasswordException(ErrorCode.WRONG_PASSWORD);
        }
    }

    public LoginResponseDto reIssueAccessToken(String nickname, String refreshToken) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        jwtProvider.checkRefreshToken(account.getKakaoId(), refreshToken);
        String accessToken = jwtProvider.createAccessToken(account.getKakaoId(), account.getRole());
        return LoginResponseDto.createDto(accessToken, refreshToken);
    }

    public TempResponse logout(String accessToken) {
        jwtProvider.logout(accessToken);
        return new TempResponse("로그아웃 완료", HttpStatus.OK);
    }

    public UserInfoResponseDto getUserInfo(String nickname) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        return UserInfoResponseDto.createDto(account);
    }

    public NicknameDuplicateDto isDuplicateNickname(String nickname) {
        if (accountRepository.findByNickname(nickname).isPresent()) {
            return NicknameDuplicateDto.createDto(nickname, true);
        }
        return NicknameDuplicateDto.createDto(nickname, false);
    }

    public void checkUserExist(String nickname) {
        if (!accountRepository.existsByNickname(nickname)) {
            throw new NoSuchUserException(ErrorCode.NO_SUCH_USER);
        }
    }
}
