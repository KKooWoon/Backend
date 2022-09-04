package wit.shortterm1.kkoowoon.domain.race.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.race.dto.request.RaceUpdateDto;
import wit.shortterm1.kkoowoon.domain.race.dto.response.*;
import wit.shortterm1.kkoowoon.domain.race.dto.request.RaceCreateDto;
import wit.shortterm1.kkoowoon.domain.race.exception.*;
import wit.shortterm1.kkoowoon.domain.race.persist.Participate;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.race.repository.ParticipateRepository;
import wit.shortterm1.kkoowoon.domain.race.repository.RaceInvitationRepository;
import wit.shortterm1.kkoowoon.domain.race.repository.RaceRepository;
import wit.shortterm1.kkoowoon.domain.user.dto.response.UserInfoDto;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.workout.exception.NoSuchRecordException;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;
import wit.shortterm1.kkoowoon.domain.workout.repository.WorkoutRecordRepository;
import wit.shortterm1.kkoowoon.global.common.jwt.JwtProvider;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RaceService {
    private final RaceRepository raceRepository;
    private final RaceInvitationRepository raceInvitationRepository;
    private final ParticipateRepository participateRepository;
    private final AccountRepository accountRepository;
    private final WorkoutRecordRepository workoutRecordRepository;
    private final JwtProvider jwtProvider;

    public RaceInfoDto getRaceInfo(Long raceId) {
        return RaceInfoDto.createDto(getRace(raceId));
    }

    public RaceInfoWithParticipateDto getRaceInfoWithParticipateOrNot(HttpServletRequest request, Long raceId) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = getAccountByKakaoId(kakaoId);
        boolean participateOrNot = participateRepository.findByAccountAndRace(account.getId(), raceId).isPresent();
        Race race = getRace(raceId);
        return RaceInfoWithParticipateDto.createDto(race, participateOrNot);
    }

    @Transactional
    public RaceCreateResultDto createRace(HttpServletRequest request, RaceCreateDto raceCreateDto) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = getAccountByKakaoId(kakaoId);
        checkRaceDuration(raceCreateDto.getStartedAt(), raceCreateDto.getEndedAt());

        String raceCode = createRaceCode();
        boolean isPrivate = !(raceCreateDto.getRacePassword().isEmpty() || raceCreateDto.getRacePassword().equals("") || raceCreateDto.getRacePassword() == null);
        Race race = Race.of(raceCreateDto.getStartedAt(), raceCreateDto.getEndedAt(), raceCreateDto.getRaceName(),
                raceCode, raceCreateDto.getRacePassword(), account.getNickname(), raceCreateDto.getRaceTag(), isPrivate, raceCreateDto.getDescription());
        Race newRace = raceRepository.save(race);
        participateRepository.save(Participate.of(account, newRace));
        return RaceCreateResultDto
                .createDto(true, race);
    }

    private Account getAccountByKakaoId(String kakaoId) {
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        return account;
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
    }

    @Transactional
    public RaceParticipateResultDto participateRace(HttpServletRequest request, String raceCode, String racePassword) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = getAccountByKakaoId(kakaoId);
        Race race = getRaceByRaceCode(raceCode);

        checkRacePassword(race.getRacePassword(), racePassword);
        checkAlreadyParticipateRace(account.getId(), race.getId());

        Participate participate = participateRepository.save(Participate.of(account, race));
        race.addMemberCount();
        return RaceParticipateResultDto.createDto(true, race.getRaceOwner(), race.getId(), participate.getCreatedAt());
    }

    @Transactional
    public RaceLeaveResultDto leaveRace(HttpServletRequest request, String raceCode) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = getAccountByKakaoId(kakaoId);
        Race race = getRaceByRaceCode(raceCode);

        checkUserExistInRace(account.getId(), race.getId());
        checkLeaveRacePossible(account, race);

        Participate participate = participateRepository.findByAccountAndRace(account.getId(), race.getId())
                .orElseThrow(() -> new NoSuchParticipateException(ErrorCode.NO_SUCH_PARTICIPATE));

        participateRepository.delete(participate);
        race.subtractMemberCount();

        return RaceLeaveResultDto.createDto(true, account.getNickname(), race.getName(), LocalDateTime.now());
    }

    @Transactional
    public RaceDeleteResultDto deleteRace(HttpServletRequest request, String raceCode, String racePassword) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = getAccountByKakaoId(kakaoId);
        Race race = getRaceByRaceCode(raceCode);
        checkEditRacePossible(account, race);
        checkRacePassword(race.getRacePassword(), racePassword);

        raceRepository.delete(race);

        return RaceDeleteResultDto.createDto(true, LocalDateTime.now(), account.getNickname(), raceCode, race.getName());
    }

    public CurrentRaceListDto findCurrentRaceList(List<Participate> participateList) {
        CurrentRaceListDto currentRaceListDto = CurrentRaceListDto.createDto();
        participateList
                .stream()
                .filter(p -> isCurrentRaceWithDate(LocalDate.now(), p))
                .forEach(p -> currentRaceListDto.addRace(RaceInfoDto.createDto(p.getRace())));

        return currentRaceListDto;
    }

    public CurrentRaceListDto findCurrentRaceList(Long accountId) {
        CurrentRaceListDto currentRaceListDto = CurrentRaceListDto.createDto();
        participateRepository.findAllByAccountId(accountId)
                .stream()
                .filter(p -> isCurrentRaceWithDate(LocalDate.now(), p))
                .forEach(p -> currentRaceListDto.addRace(RaceInfoDto.createDto(p.getRace())));

        return currentRaceListDto;
    }

    public CurrentRaceWithConfirmListDto findCurrentRaceListWithConfirm(Long accountId, LocalDate date) {
        CurrentRaceWithConfirmListDto currentRaceWithConfirmListDto = CurrentRaceWithConfirmListDto.createDto();
        participateRepository.findAllByAccountId(accountId)
                .stream()
                .filter(p -> isCurrentRaceWithDate(date, p))
                .forEach(p -> {
                    WorkoutRecord workoutRecord = workoutRecordRepository.findByAccountNRaceNDate(accountId, p.getRace().getId(), date)
                            .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WORKOUT_RECORD));
                    currentRaceWithConfirmListDto.addRace(RaceInfoWithConfirmDto.createDto(p.getRace(), workoutRecord.isConfirmed()));
                });

        return currentRaceWithConfirmListDto;
    }

    public Race getRace(Long raceId) {
        return raceRepository.findById(raceId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_RACE));
    }

    public PastRaceListDto findPastRaceList(Long accountId, LocalDate date) {
        PastRaceListDto pastRaceListDto = PastRaceListDto.createDto();
        participateRepository.findAllByAccountId(accountId)
                .stream()
                .filter(p -> !isCurrentRaceWithDate(date, p))
                .forEach(p -> pastRaceListDto.addRace(RaceInfoDto.createDto(p.getRace())));

        return pastRaceListDto;
    }

    public AllRaceListDto findAllRaceList(Long accountId, LocalDate date) {
        PastRaceListDto pastRaceListDto = PastRaceListDto.createDto();
        CurrentRaceWithConfirmListDto dto = CurrentRaceWithConfirmListDto.createDto();
        participateRepository.findAllByAccountId(accountId)
                .forEach(p -> {
                    if (!isCurrentRaceWithDate(date, p)) pastRaceListDto.addRace(RaceInfoDto.createDto(p.getRace()));
                    else {
                        workoutRecordRepository.findByAccountNRaceNDate(accountId, p.getRace().getId(), date)
                                .ifPresentOrElse(workoutRecord -> dto.addRace(RaceInfoWithConfirmDto.createDto(p.getRace(), workoutRecord.isConfirmed())),
                                        () -> dto.addRace(RaceInfoWithConfirmDto.createDto(p.getRace(), false)));
                    }
                });
        return AllRaceListDto.createDto(dto, pastRaceListDto);
    }

    @Transactional
    public RaceUpdateResultDto updateRace(HttpServletRequest request, Long raceId, RaceUpdateDto raceUpdateDto) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = getAccountByKakaoId(kakaoId);
        Race race = getRace(raceId);

        checkEditRacePossible(account, race);

        race.updateRace(raceUpdateDto);
        Race updatedRace = raceRepository.save(race);
        return RaceUpdateResultDto.createDto(true, updatedRace);
    }

    private Race getRaceByRaceCode(String raceCode) {
        return raceRepository.findByRaceCode(raceCode)
                .orElseThrow(() -> new NoSuchRaceException(ErrorCode.NO_SUCH_RACE));
    }

    private void checkEditRacePossible(Account account, Race race) {
        if (!isRaceOwner(account, race)) {
            throw new IllegalRaceException(ErrorCode.EDIT_RACE_OWNER_ONLY);
        }
    }

    private void checkLeaveRacePossible(Account account, Race race) {
        if (isRaceOwner(account, race)) {
            throw new IllegalRaceException(ErrorCode.OWNER_CANT_LEAVE_RACE);
        }
    }

    private boolean isRaceOwner(Account account, Race race) {
        return account.getNickname().equals(race.getRaceOwner());
    }

    private void checkUserExistInRace(Long accountId, Long raceId) {
        if (!participateRepository.existsByAccountAndRace(accountId, raceId)) {
            throw new NoSuchParticipateException(ErrorCode.NO_SUCH_USER_IN_RACE);
        }
    }

    private void checkAlreadyParticipateRace(Long accountId, Long raceId) {
        if (participateRepository.existsByAccountAndRace(accountId, raceId)) {
            throw new IllegalRaceException(ErrorCode.ALREADY_PARTICIPATE);
        }
    }

    private void checkRacePassword(String realPW, String targetPW) {
        if (!realPW.equals(targetPW)) {
            throw new WrongRacePasswordException(ErrorCode.WRONG_RACE_PASSWORD);
        }
    }

    private String createRaceCode() {
        String invitationCode = makeRandomCode();
        List<String> all = raceInvitationRepository.findAllCode();
        while (all.contains(invitationCode)) {
            invitationCode = makeRandomCode();
        }
        return invitationCode;
    }

    private void checkRaceDuration(LocalDate startedAt, LocalDate endedAt) {
        if (startedAt.isAfter(endedAt)) {
            throw new IllegalRaceException(ErrorCode.TIME_REVERSAL_ERROR);
        }
    }

    private String makeRandomCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append((char) ((Math.random() * 26) + 65)); // A ~ Z
        }
        return builder.toString();
    }

    private boolean isCurrentRaceWithDate(LocalDate date, Participate p) {
        return date.isBefore(p.getRace().getEndedAt().plusDays(1L));
    }

    public CurrentRaceListDto findRaceListWithName(String name) {
        CurrentRaceListDto currentRaceListDto = CurrentRaceListDto.createDto();
        raceRepository.findAllByName("%" + name + "%")
                .forEach(race -> currentRaceListDto.addRace(RaceInfoDto.createDto(race)));
        return currentRaceListDto;
    }

    public CurrentRaceListDto findRaceListWithTag(String tag) {
        CurrentRaceListDto currentRaceListDto = CurrentRaceListDto.createDto();
        raceRepository.findAllByTag(tag)
                .forEach(race -> currentRaceListDto.addRace(RaceInfoDto.createDto(race)));
        return currentRaceListDto;
    }

    public RaceRankingDto getRanking(Long raceId) {
        List<Participate> participateList = participateRepository.findAllByRaceId(raceId);
        participateList.sort(Comparator.comparing(Participate::getTotalScore, Comparator.reverseOrder()));
        List<UserInfoWithScoreDto> list = new ArrayList<>();
        participateList.forEach(p -> list.add(UserInfoWithScoreDto.createDto(p.getAccount(), p)));
        return RaceRankingDto.createDto(raceId, list);
    }

    public RaceParticipantsDto getParticipants(Long raceId) {
        List<Participate> participateList = participateRepository.findAllByRaceId(raceId);
        participateList.sort(Comparator.comparing(Participate::getTotalScore, Comparator.reverseOrder()));
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        participateList.forEach(p -> userInfoDtoList.add(UserInfoDto.createDto(p.getAccount())));
        return RaceParticipantsDto.createDto(raceId, userInfoDtoList);
    }
}
