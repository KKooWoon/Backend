package wit.shortterm1.kkoowoon.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowInfoDto;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowingListDto;
import wit.shortterm1.kkoowoon.domain.etc.persist.Follow;
import wit.shortterm1.kkoowoon.domain.etc.repository.FollowRepository;
import wit.shortterm1.kkoowoon.domain.etc.service.ImageService;
import wit.shortterm1.kkoowoon.domain.race.dto.response.CurrentRaceListDto;
import wit.shortterm1.kkoowoon.domain.race.persist.Participate;
import wit.shortterm1.kkoowoon.domain.race.repository.ParticipateRepository;
import wit.shortterm1.kkoowoon.domain.race.repository.RaceRepository;
import wit.shortterm1.kkoowoon.domain.race.service.RaceService;
import wit.shortterm1.kkoowoon.domain.user.dto.KakaoUserInfo;
import wit.shortterm1.kkoowoon.domain.user.dto.response.*;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchKakaoUserException;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final ParticipateRepository participateRepository;
    private final ImageService imageService;

    public Account getAccountByKakaoId(String kakaoId) {
        return accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
    }

    public LoginResponseDto login(String code) {
        KakaoUserInfo kakaoUserInfo = oauthService.findKakaoUser(oauthService.getKakaoAccessToken(code));
        Account account = accountRepository.findByKakaoId(kakaoUserInfo.getKakaoId())
                .orElseThrow(() -> new NoSuchKakaoUserException(kakaoUserInfo.getKakaoId(), kakaoUserInfo.getPhotoUrl(), ErrorCode.NO_SUCH_USER));
        String accessToken = jwtProvider.createAccessToken(account.getKakaoId(), account.getRole());
        String refreshToken = jwtProvider.createRefreshToken(account.getKakaoId(), account.getRole());
        return LoginResponseDto.createDto(accessToken, refreshToken, account);
    }

    @Transactional
    public SignUpResultDto signUp(SignUpRequestDto signUpReqDto) {
        checkKakaoIdIsDuplicate(signUpReqDto.getKakaoId());
        Account savedAccount = accountRepository.save(signUpReqDto.toEntity());
        String accessToken = jwtProvider.createAccessToken(savedAccount.getKakaoId(), savedAccount.getRole());
        String refreshToken = jwtProvider.createRefreshToken(savedAccount.getKakaoId(), savedAccount.getRole());
        return SignUpResultDto.createDto(true, savedAccount.getCreatedAt(), accessToken,
                refreshToken, UserInfoDto.createDto(savedAccount));
    }

    public LoginResponseDto reIssueAccessToken(Long accountId, String refreshToken) {
        Account account = getAccount(accountId);
        jwtProvider.checkRefreshToken(account.getKakaoId(), refreshToken);
        String accessToken = jwtProvider.createAccessToken(account.getKakaoId(), account.getRole());
        return LoginResponseDto.createDto(accessToken, refreshToken, account);
    }

    public LogoutResultDto logout(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        jwtProvider.logout(accessToken);
        return LogoutResultDto.createDto(true, LocalDateTime.now());
    }

    public UserInfoDto getUserInfo(Long accountId) {
        Account account = getAccount(accountId);
        return UserInfoDto.createDto(account);
    }

    public MainPageInfoDto getMainPageInfo(Long accountId) {
        Account account = getAccount(accountId);
        List<Participate> participateList = participateRepository.findAllByAccountId(accountId);
        WorkoutRecordDto workoutRecordDto = null;
        if (!participateList.isEmpty()) {
            workoutRecordDto = getWorkoutRecordDto(accountId, participateList.get(0).getId());
        }
        CurrentRaceListDto currentRaceListDto = raceService.findCurrentRaceList(participateList);
//        WorkoutRecord workoutRecord = workoutRecordRepository.findByAccountNDate(account, LocalDate.now()).orElse(null);
        FollowingListDto followingListDto = FollowingListDto.createDto();
        followRepository.findFollowingListBySource(account).forEach((follow -> followingListDto.addFollowing(FollowInfoDto.createDto(account, follow.getFollowing()))));

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

    private WorkoutRecordDto getWorkoutRecordDto(Long accountId, Long raceId) {
        WorkoutRecordDto workoutRecordDto;
        try {
            workoutRecordDto = workoutRecordService.findWorkoutRecord(accountId, raceId, LocalDate.of(2022, 7, 21));
        } catch (NoSuchRecordException e) {
            workoutRecordDto = null;
        }
        return workoutRecordDto;
    }

    private void checkKakaoIdIsDuplicate(String kakaoId) {
        if(accountRepository.existsByKakaoId(kakaoId)) {
            throw new UserDuplicateException(ErrorCode.USER_ALREADY_EXIST);
        }
    }

    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if(!isSame) {
            throw new WrongPasswordException(ErrorCode.WRONG_PASSWORD);
        }
    }

    public UserInfoWithFollowListDto findUserListByNickname(HttpServletRequest request, String nickname) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account sourceAccount = getAccountByKakaoId(kakaoId);
        UserInfoWithFollowListDto listDto = UserInfoWithFollowListDto.createDto();
        accountRepository.findAllByName("%" + nickname + "%")
                .forEach(account -> {
                    boolean isFollow = followRepository.findFollowByTwo(sourceAccount.getId(), account.getId()).isPresent();
                    listDto.addUserInfoWithFollow(UserInfoWithFollowDto.createDto(account.getId(), account.getNickname(), account.getPhotoUrl(), isFollow));
                });
        return listDto;
    }
}
