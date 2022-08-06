package wit.shortterm1.kkoowoon.domain.workout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.workout.dto.CardioDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.DietDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.FoodDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateCardioDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateDietDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.CardioCreateResultDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.DietCreateResultDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.WorkoutDeleteResultDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.WorkoutRecordDto;
import wit.shortterm1.kkoowoon.domain.workout.exception.IllegalWorkoutArgumentException;
import wit.shortterm1.kkoowoon.domain.workout.exception.NoSuchRecordException;
import wit.shortterm1.kkoowoon.domain.workout.persist.*;
import wit.shortterm1.kkoowoon.domain.workout.repository.*;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutRecordService {

    private final WorkoutRecordRepository workoutRecordRepository;
    private final AccountRepository accountRepository;
    private final CardioRepository cardioRepository;
    private final WeightRepository weightRepository;
    private final DietRepository dietRepository;
    private final FoodRepository foodRepository;

    public WorkoutRecordDto findWorkoutRecord(String nickname, LocalDate date) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        WorkoutRecord workoutRecord = workoutRecordRepository.findByAccountNDate(account, date)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WORKOUT_RECORD));
        WorkoutRecordDto workoutRecordDto = WorkoutRecordDto.createDto(workoutRecord);
        dietRepository.findByRecordId(workoutRecord.getId()).forEach((diet) -> {
            DietDto dietDto = DietDto.createDto(diet);
            foodRepository.findAllByDiet(diet).forEach((food) -> dietDto.addFoodDto(FoodDto.createDto(food)));
            workoutRecordDto.addDietDto(dietDto);
        });
//        weightRepository.findByRecordId(workoutRecord.getId()).forEach((weight -> {
//        }));
        cardioRepository.findByRecordId(workoutRecord.getId())
                .forEach((cardio -> workoutRecordDto.addCardioDto(CardioDto.createDto(cardio))));
        return workoutRecordDto;
    }

    @Transactional
    public CardioCreateResultDto createCardioRecord(String nickname, LocalDate date, CreateCardioDto createCardioDto) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        WorkoutRecord workoutRecord = findOrCreateWorkoutRecord(date, account);
        Cardio cardio = Cardio.of(createCardioDto.getName(), createCardioDto.getTime(), createCardioDto.getCalorie(), workoutRecord);
        cardioRepository.save(cardio);

        return CardioCreateResultDto.createDto(true, cardio.getId(), cardio.getCreatedAt(), date);
    }

    @Transactional
    public WorkoutDeleteResultDto deleteCardioRecord(String nickname, Long cardioId) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        Cardio cardio = cardioRepository.findByIdWithRecord(cardioId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_CARDIO_RECORD));
        checkCardioOwnerOrNot(account, cardio);
        cardioRepository.delete(cardio);
        return WorkoutDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    @Transactional
    public DietCreateResultDto createDietRecord(String nickname, LocalDate date, CreateDietDto createDietDto) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        WorkoutRecord workoutRecord = findOrCreateWorkoutRecord(date, account);
        Diet savedDiet = dietRepository.save(Diet.of(createDietDto.getName(), workoutRecord));
        createDietDto.getFoodList().forEach((foodDto ->
                foodRepository.save(Food.of(foodDto.getName(), foodDto.getWeight(), foodDto.getCalorie(), savedDiet))));
        return DietCreateResultDto.createDto(true, savedDiet, date);
    }

    @Transactional
    public WorkoutDeleteResultDto deleteDietRecord(String nickname, Long dietId) {
        Account account = accountRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        Diet diet = dietRepository.findByIdWithRecord(dietId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_DIET_RECORD));
        checkDietOwnerOrNot(account, diet);
        dietRepository.delete(diet);
        return WorkoutDeleteResultDto.createDto(true, LocalDateTime.now());
    }
    @Transactional
    protected WorkoutRecord findOrCreateWorkoutRecord(LocalDate date, Account account) {
        WorkoutRecord workoutRecord = workoutRecordRepository
                .findByAccountNDate(account, date)
                .orElse(null);
        if (workoutRecord == null) {
            workoutRecord = WorkoutRecord.of(false, 0.0, date, account);
            workoutRecordRepository.save(workoutRecord);
        }
        return workoutRecord;
    }

    private void checkCardioOwnerOrNot(Account account, Cardio cardio) {
        if (!cardio.getWorkoutRecord().getAccount().getId().equals(account.getId())) {
            throw new IllegalWorkoutArgumentException(ErrorCode.DELETE_RECORD_MYSELF_ONLY);
        }
    }

    private void checkDietOwnerOrNot(Account account, Diet diet) {
        if (!diet.getWorkoutRecord().getAccount().getId().equals(account.getId())) {
            throw new IllegalWorkoutArgumentException(ErrorCode.DELETE_RECORD_MYSELF_ONLY);
        }
    }
}
