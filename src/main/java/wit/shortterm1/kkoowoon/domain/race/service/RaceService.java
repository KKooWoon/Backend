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
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.workout.exception.NoSuchRecordException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RaceService {
    private final RaceRepository raceRepository;
    private final RaceInvitationRepository raceInvitationRepository;
    private final ParticipateRepository participateRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public RaceCreateResultDto createRace(Long ownerId, RaceCreateDto raceCreateDto) {
        Account account = getAccount(ownerId);
        checkRaceDuration(raceCreateDto.getStartedAt(), raceCreateDto.getEndedAt());

        String raceCode = createRaceCode();
        Race race = Race.of(raceCreateDto.getStartedAt(), raceCreateDto.getEndedAt(), raceCreateDto.getRaceName(),
                raceCode, raceCreateDto.getRacePassword(), account.getNickname(), raceCreateDto.getRaceTag());
        Race newRace = raceRepository.save(race);
        participateRepository.save(Participate.of(account, newRace));
        return RaceCreateResultDto
                .createDto(true, race);
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
    }

    @Transactional
    public RaceParticipateResultDto participateRace(Long accountId, String raceCode, String racePassword) {
        Account account = getAccount(accountId);
        Race race = getRaceByRaceCode(raceCode);

        checkRacePassword(race.getRacePassword(), racePassword);
        checkAlreadyParticipateRace(account.getId(), race.getId());

        Participate participate = participateRepository.save(Participate.of(account, race));
        race.addMemberCount();
        return RaceParticipateResultDto.createDto(true, account.getNickname(), race.getName(), participate.getCreatedAt());
    }

    @Transactional
    public RaceLeaveResultDto leaveRace(Long accountId, String raceCode) {
        Account account = getAccount(accountId);
        Race race = getRaceByRaceCode(raceCode);

        checkUserExistInRace(account.getId(), race.getId());
        checkLeaveRacePossible(account, race);

        Participate participate = participateRepository.findByAccountAndRace(account, race)
                .orElseThrow(() -> new NoSuchParticipateException(ErrorCode.NO_SUCH_PARTICIPATE));

        participateRepository.delete(participate);
        race.subtractMemberCount();

        return RaceLeaveResultDto.createDto(true, account.getNickname(), race.getName(), LocalDateTime.now());
    }

    @Transactional
    public RaceDeleteResultDto deleteRace(Long accountId, String raceCode, String racePassword) {
        Account account = getAccount(accountId);
        Race race = getRaceByRaceCode(raceCode);
        checkEditRacePossible(account, race);
        checkRacePassword(race.getRacePassword(), racePassword);

        raceRepository.delete(race);

        return RaceDeleteResultDto.createDto(true, LocalDateTime.now(), account.getNickname(), raceCode, race.getName());
    }

    public CurrentRaceListDto findCurrentRaceList(Long accountId) {
        Account account = getAccount(accountId);
        CurrentRaceListDto currentRaceListDto = CurrentRaceListDto.createDto();
        participateRepository.findAllByAccount(account)
                .stream()
                .filter(this::isCurrentRace)
                .forEach(p -> currentRaceListDto.addRace(RaceInfoDto.createDto(p.getRace())));

        return currentRaceListDto;
    }

    public Race getRace(Long raceId) {
        return raceRepository.findById(raceId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_RACE));
    }

    public PastRaceListDto findPastRaceList(Long accountId) {
        Account account = getAccount(accountId);
        PastRaceListDto pastRaceListDto = PastRaceListDto.createDto();
        participateRepository.findAllByAccount(account)
                .stream()
                .filter(this::isPastRace)
                .forEach(p -> pastRaceListDto.addRace(RaceInfoDto.createDto(p.getRace())));

        return pastRaceListDto;
    }

    public AllRaceListDto findAllRaceList(Long accountId) {
        Account account = getAccount(accountId);
        PastRaceListDto pastRaceListDto = PastRaceListDto.createDto();
        CurrentRaceListDto currentRaceListDto = CurrentRaceListDto.createDto();
        participateRepository.findAllByAccount(account)
                .forEach(p -> {
                    if (isPastRace(p)) pastRaceListDto.addRace(RaceInfoDto.createDto(p.getRace()));
                    else currentRaceListDto.addRace(RaceInfoDto.createDto(p.getRace()));
                });
        return AllRaceListDto.createDto(currentRaceListDto, pastRaceListDto);
    }

    @Transactional
    public RaceUpdateResultDto updateRace(Long ownerId, Long raceId, RaceUpdateDto raceUpdateDto) {
        Account account = getAccount(ownerId);
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

    private boolean isCurrentRace(Participate p) {
        return p.getRace().getEndedAt().isAfter(LocalDate.now().minusDays(1L))
                && p.getRace().getStartedAt().isBefore(LocalDate.now().plusDays(1L));
    }

    private boolean isPastRace(Participate p) {
        return LocalDate.now().isAfter(p.getRace().getEndedAt());
    }
}
