package wit.shortterm1.kkoowoon.domain.race.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RaceService {
    private final AccountRepository accountRepository;
    private final RaceRepository raceRepository;
    private final RaceInvitationRepository raceInvitationRepository;
    private final ParticipateRepository participateRepository;

    @Transactional
    public RaceCreateResultDto createRace(String ownerNickname, RaceCreateDto raceCreateDto) {
        Account account = accountRepository.findByNickname(ownerNickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        checkRaceDuration(raceCreateDto.getStartedAt(), raceCreateDto.getEndedAt());

        String raceCode = createRaceCode();
        Race race = Race.of(raceCreateDto.getStartedAt(), raceCreateDto.getEndedAt(), 1,
                raceCreateDto.getRaceName(), raceCode, raceCreateDto.getRacePassword(), ownerNickname, raceCreateDto.getRaceTag());
        Race newRace = raceRepository.save(race);
        participateRepository.save(Participate.of(account, race));
        return RaceCreateResultDto
                .createDto(true, newRace.getId(), LocalDateTime.now(), ownerNickname,
                        raceCode, newRace.getRacePassword(), newRace.getName());
    }

    @Transactional
    public RaceParticipateResultDto participateRace(String nickname, String raceCode, String racePassword) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        Race race = raceRepository.findByRaceCode(raceCode)
                .orElseThrow(() -> new NoSuchRaceException(ErrorCode.NO_SUCH_RACE));

        checkRacePassword(race.getRacePassword(), racePassword);
        checkAlreadyParticipateRace(account, race);

        Participate participate = participateRepository.save(Participate.of(account, race));
        race.addMemberCount();
        return RaceParticipateResultDto.createDto(true, nickname, race.getName(), participate.getCreatedAt());
    }

    @Transactional
    public RaceLeaveResultDto leaveRace(String nickname, String raceCode) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        Race race = raceRepository.findByRaceCode(raceCode)
                .orElseThrow(() -> new NoSuchRaceException(ErrorCode.NO_SUCH_RACE));

        checkUserExistInRace(account, race);
        checkLeaveRacePossible(account, race);

        Participate participate = participateRepository.findByAccountAndRace(account, race)
                .orElseThrow(() -> new NoSuchParticipateException(ErrorCode.NO_SUCH_PARTICIPATE));

        participateRepository.delete(participate);
        race.subtractMemberCount();

        return RaceLeaveResultDto.createDto(true, nickname, race.getName(), LocalDateTime.now());
    }

    @Transactional
    public RaceDeleteResultDto deleteRace(String ownerNickname, String raceCode, String racePassword) {
        Account account = accountRepository.findByNickname(ownerNickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        Race race = raceRepository.findByRaceCode(raceCode)
                .orElseThrow(() -> new NoSuchRaceException(ErrorCode.NO_SUCH_RACE));
        checkDeleteRacePossible(account, race);
        checkRacePassword(race.getRacePassword(), racePassword);

        raceRepository.delete(race);

        return RaceDeleteResultDto.createDto(true, LocalDateTime.now(), ownerNickname, raceCode, race.getName());
    }

    private void checkDeleteRacePossible(Account account, Race race) {
        if (!isRaceOwner(account, race)) {
            throw new IllegalRaceException(ErrorCode.DELETE_RACE_OWNER_ONLY);
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

    private void checkUserExistInRace(Account account, Race race) {
        if (!participateRepository.existsByAccountAndRace(account, race)) {
            throw new NoSuchParticipateException(ErrorCode.NO_SUCH_USER_IN_RACE);
        }
    }

    private void checkAlreadyParticipateRace(Account account, Race race) {
        if (participateRepository.existsByAccountAndRace(account, race)) {
            throw new IllegalRaceException(ErrorCode.ALREADY_PARTICIPATE);
        }
    }

    private void checkRacePassword(String realPW, String targetPW) {
        if (!realPW.equals(targetPW)) {
            throw new WrongRacePasswordException(ErrorCode.WRONG_RACE_PASSWORD);
        }
    }

    private void checkUserExist(String ownerNickname) {
        if (!accountRepository.existsByNickname(ownerNickname)) {
            throw new NoSuchUserException(ErrorCode.NO_SUCH_USER);
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

    public CurrentRaceListDto findCurrentRaceList(String nickname) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        CurrentRaceListDto currentRaceListDto = CurrentRaceListDto.createDto();
        participateRepository.findAllByAccount(account)
                .stream()
                .filter(this::isCurrentRace)
                .forEach(p -> currentRaceListDto.addRace(RaceInfoDto.createDto(p.getRace())));

        return currentRaceListDto;
    }

    public PastRaceListDto findPastRaceList(String nickname) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        PastRaceListDto pastRaceListDto = PastRaceListDto.createDto();
        participateRepository.findAllByAccount(account)
                .stream()
                .filter(this::isPastRace)
                .forEach(p -> pastRaceListDto.addRace(RaceInfoDto.createDto(p.getRace())));

        return pastRaceListDto;
    }

    public AllRaceListDto findAllRaceList(String nickname) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        PastRaceListDto pastRaceListDto = PastRaceListDto.createDto();
        CurrentRaceListDto currentRaceListDto = CurrentRaceListDto.createDto();
        participateRepository.findAllByAccount(account)
                .forEach(p -> {
                    if (isPastRace(p)) pastRaceListDto.addRace(RaceInfoDto.createDto(p.getRace()));
                    else currentRaceListDto.addRace(RaceInfoDto.createDto(p.getRace()));
                });
        return AllRaceListDto.createDto(currentRaceListDto, pastRaceListDto);
    }

    private boolean isCurrentRace(Participate p) {
        return p.getRace().getEndedAt().isAfter(LocalDate.now().minusDays(1L))
                && p.getRace().getStartedAt().isBefore(LocalDate.now().plusDays(1L));
    }

    private boolean isPastRace(Participate p) {
        return LocalDate.now().isAfter(p.getRace().getEndedAt());
    }
}
