package wit.shortterm1.kkoowoon.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowInfoDto;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowingListDto;
import wit.shortterm1.kkoowoon.domain.etc.persist.Follow;
import wit.shortterm1.kkoowoon.domain.etc.repository.FollowRepository;
import wit.shortterm1.kkoowoon.domain.race.dto.response.CurrentRaceListDto;
import wit.shortterm1.kkoowoon.domain.race.repository.RaceRepository;
import wit.shortterm1.kkoowoon.domain.race.service.RaceService;
import wit.shortterm1.kkoowoon.domain.user.dto.response.*;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.dto.request.SignUpRequestDto;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.exception.UserDuplicateException;
import wit.shortterm1.kkoowoon.domain.user.exception.WrongPasswordException;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.WorkoutRecordDto;
import wit.shortterm1.kkoowoon.domain.workout.exception.NoSuchRecordException;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;
import wit.shortterm1.kkoowoon.domain.workout.repository.WorkoutRecordRepository;
import wit.shortterm1.kkoowoon.domain.workout.service.WorkoutRecordService;
import wit.shortterm1.kkoowoon.global.common.jwt.JwtProvider;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final OauthService oauthService;
    private final FollowRepository followRepository;
    private final RaceService raceService;
    private final WorkoutRecordService workoutRecordService;

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

    public LoginResponseDto reIssueAccessToken(Long accountId, String refreshToken) {
        Account account = getAccount(accountId);
        jwtProvider.checkRefreshToken(account.getKakaoId(), refreshToken);
        String accessToken = jwtProvider.createAccessToken(account.getKakaoId(), account.getRole());
        return LoginResponseDto.createDto(accessToken, refreshToken);
    }

    public TempResponse logout(String accessToken) {
        jwtProvider.logout(accessToken);
        return new TempResponse("로그아웃 완료", HttpStatus.OK);
    }

    public LoginResponseDto login(String kakaoId) {
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
//        checkPassword(password, account.getPassword());
        String accessToken = jwtProvider.createAccessToken(account.getKakaoId(), account.getRole());
        String refreshToken = jwtProvider.createRefreshToken(account.getKakaoId(), account.getRole());
        return LoginResponseDto.createDto(accessToken, refreshToken);
    }

    public UserInfoDto getUserInfo(Long accountId) {
        Account account = getAccount(accountId);
        return UserInfoDto.createDto(account);
    }

    public MainPageInfoDto getMainPageInfo(Long accountId) {
        Account account = getAccount(accountId);
//        WorkoutRecord workoutRecord = workoutRecordRepository.findByAccountNDate(account, LocalDate.now()).orElse(null);
        WorkoutRecordDto workoutRecordDto = getWorkoutRecordDto(accountId);
        FollowingListDto followingListDto = FollowingListDto.createDto();
        followRepository.findFollowingListBySource(account).forEach((follow -> followingListDto.addFollowing(FollowInfoDto.createDto(account, follow.getFollowing()))));
        CurrentRaceListDto currentRaceListDto = raceService.findCurrentRaceList(accountId);

        return MainPageInfoDto
                .createDto(UserInfoDto.createDto(account), workoutRecordDto, followingListDto, currentRaceListDto);
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

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
    }

    private WorkoutRecordDto getWorkoutRecordDto(Long accountId) {
        WorkoutRecordDto workoutRecordDto;
        try {
            workoutRecordDto = workoutRecordService.findWorkoutRecord(accountId, LocalDate.of(2022, 7, 21));
        } catch (NoSuchRecordException e) {
            workoutRecordDto = null;
        }
        return workoutRecordDto;
    }

    private void checkPasswordConvention(String password) {
        // TODO: Check Password Convention
    }

    private void checkKakaoIdIsDuplicate(String kakaoId) {
        boolean isDuplicate = accountRepository.existsByKakaoId(kakaoId);
        if(isDuplicate) {
            throw new UserDuplicateException(ErrorCode.USER_ALREADY_EXIST);
        }
    }

    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if(!isSame) {
            throw new WrongPasswordException(ErrorCode.WRONG_PASSWORD);
        }
    }
}
