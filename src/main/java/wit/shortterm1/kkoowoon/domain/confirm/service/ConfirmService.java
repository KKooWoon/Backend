package wit.shortterm1.kkoowoon.domain.confirm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.confirm.dto.request.ConfirmCreateDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.ConfirmCheckResultDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.ConfirmCreateResultDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.ConfirmDeleteResultDto;
import wit.shortterm1.kkoowoon.domain.confirm.exception.AlreadyConfirmedException;
import wit.shortterm1.kkoowoon.domain.confirm.exception.NoSuchConfirmationException;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;
import wit.shortterm1.kkoowoon.domain.confirm.repository.ConfirmRepository;
import wit.shortterm1.kkoowoon.domain.race.exception.NoSuchParticipateException;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.race.repository.ParticipateRepository;
import wit.shortterm1.kkoowoon.domain.race.service.RaceService;
import wit.shortterm1.kkoowoon.domain.workout.persist.*;
import wit.shortterm1.kkoowoon.domain.workout.service.WorkoutRecordService;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfirmService {

    private final RaceService raceService;
    private final WorkoutRecordService workoutRecordService;
    private final ConfirmRepository confirmRepository;
    private final ParticipateRepository participateRepository;

    @Transactional
    public ConfirmCreateResultDto createConfirmation(Long accountId, Long recordId, Long raceId, ConfirmCreateDto dto) {
        WorkoutRecord workoutRecord = workoutRecordService.getWorkoutRecord(recordId);
        Race race = raceService.getRace(raceId);

        checkUserInRace(accountId, race.getId());   // 레이스에 참여중인 사용자인지 체크
        checkAlreadyConfirmed(workoutRecord, race); // 이미 인증했는지 체크

        Confirm savedConfirm = confirmRepository
                .save(Confirm.of(dto.getPhotoUrl1(), dto.getPhotoUrl2(), dto.getPhotoUrl3(), dto.getDescription(), race, workoutRecord));
        return ConfirmCreateResultDto.createDto(true, savedConfirm);
    }

    @Transactional
    public ConfirmDeleteResultDto deleteConfirmation(Long confirmId) {
        Confirm confirm = getConfirm(confirmId);
        confirmRepository.delete(confirm);
        return ConfirmDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    public Confirm getConfirm(Long confirmId) {
        return confirmRepository.findById(confirmId)
                .orElseThrow(() -> new NoSuchConfirmationException(ErrorCode.NO_SUCH_CONFIRMATION));
    }

    public ConfirmCheckResultDto isConfirmed(Long recordId, Long raceId) {
        Race race = raceService.getRace(raceId);
        WorkoutRecord workoutRecord = workoutRecordService.getWorkoutRecord(recordId);
        AtomicReference<ConfirmCheckResultDto> confirmCheckResultDto = new AtomicReference<>();
        confirmRepository
                .findByRaceAndRecord(race, workoutRecord).ifPresentOrElse(
                (confirmWorkout) -> confirmCheckResultDto.set(ConfirmCheckResultDto.createDto(true, confirmWorkout.getId()))
                , () -> confirmCheckResultDto.set(ConfirmCheckResultDto.createDto(false, null)));
        return confirmCheckResultDto.get();
    }

    private void checkUserInRace(Long accountId, Long raceId) {
        if (!participateRepository.existsByAccountAndRace(accountId, raceId)) {
                throw new NoSuchParticipateException(ErrorCode.NO_SUCH_PARTICIPATE);
        }
    }

    private void checkAlreadyConfirmed(WorkoutRecord workoutRecord, Race race) {
        if (confirmRepository.findByRaceAndRecord(race, workoutRecord).isPresent()) {
            throw new AlreadyConfirmedException(ErrorCode.ALREADY_CONFIRMED);
        }
    }
}