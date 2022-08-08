package wit.shortterm1.kkoowoon.domain.workout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.user.service.AccountService;
import wit.shortterm1.kkoowoon.domain.workout.dto.CardioDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.DietDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.WeightDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateCardioDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateDietDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateWeightDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.*;
import wit.shortterm1.kkoowoon.domain.workout.exception.IllegalWorkoutArgumentException;
import wit.shortterm1.kkoowoon.domain.workout.exception.NoSuchRecordException;
import wit.shortterm1.kkoowoon.domain.workout.persist.*;
import wit.shortterm1.kkoowoon.domain.workout.repository.*;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutRecordService {

    private final WorkoutRecordRepository workoutRecordRepository;
    private final CardioRepository cardioRepository;
    private final WeightRepository weightRepository;
    private final DietRepository dietRepository;
    private final FoodRepository foodRepository;
    private final WeightSetRepository weightSetRepository;
    private final AccountRepository accountRepository;

    public WorkoutRecordDto findWorkoutRecord(Long accountId, LocalDate date) {
        Account account = getAccount(accountId);
        WorkoutRecord workoutRecord = workoutRecordRepository.findByAccountNDate(account, date)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WORKOUT_RECORD));
        WorkoutRecordDto workoutRecordDto = WorkoutRecordDto.createDto(workoutRecord);
        dietRepository.findByRecord(workoutRecord).forEach((diet) -> {
            DietDto dietDto = DietDto.createDto(diet, foodRepository.findAllByDiet(diet));
            workoutRecordDto.addDietDto(dietDto);
        });
        weightRepository.findByRecord(workoutRecord).forEach(weight -> {
            WeightDto weightDto = WeightDto.createDto(weight, weightSetRepository.findAllByWeight(weight));
            workoutRecordDto.addWeightDto(weightDto);
        });
        cardioRepository.findByRecord(workoutRecord)
                .forEach((cardio -> workoutRecordDto.addCardioDto(CardioDto.createDto(cardio))));
        return workoutRecordDto;
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
    }

    @Transactional
    public CardioCreateResultDto createCardioRecord(Long accountId, LocalDate date, CreateCardioDto createCardioDto) {
        Account account = getAccount(accountId);
        WorkoutRecord workoutRecord = findOrCreateWorkoutRecord(date, account);
        Cardio cardio = Cardio.of(createCardioDto.getName(), createCardioDto.getTime(), createCardioDto.getCalorie(), workoutRecord);
        cardioRepository.save(cardio);

        return CardioCreateResultDto.createDto(true, cardio.getId(), cardio.getCreatedAt(), date);
    }

    @Transactional
    public WorkoutDeleteResultDto deleteCardioRecord(Long accountId, Long cardioId) {
        Account account = getAccount(accountId);
        Cardio cardio = cardioRepository.findByIdWithRecord(cardioId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_CARDIO_RECORD));
        checkCardioOwnerOrNot(account, cardio);
        cardioRepository.delete(cardio);
        return WorkoutDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    @Transactional
    public DietCreateResultDto createDietRecord(Long accountId, LocalDate date, CreateDietDto createDietDto) {
        Account account = getAccount(accountId);
        WorkoutRecord workoutRecord = findOrCreateWorkoutRecord(date, account);
        Diet savedDiet = dietRepository.save(Diet.of(createDietDto.getName(), workoutRecord));
        createDietDto.getFoodList().forEach((foodDto ->
                foodRepository.save(Food.of(foodDto.getName(), foodDto.getWeight(), foodDto.getCalorie(), savedDiet))));
        return DietCreateResultDto.createDto(true, savedDiet, date);
    }

    @Transactional
    public WorkoutDeleteResultDto deleteDietRecord(Long accountId, Long dietId) {
        Account account = getAccount(accountId);
        Diet diet = dietRepository.findByIdWithRecord(dietId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_DIET_RECORD));
        checkDietOwnerOrNot(account, diet);
        foodRepository.findAllByDiet(diet).forEach(foodRepository::delete);
        dietRepository.delete(diet);
        return WorkoutDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    @Transactional
    public WeightCreateResultDto createWeightRecord(Long accountId, LocalDate date, CreateWeightDto createWeightDto) {
        Account account = getAccount(accountId);
        WorkoutRecord workoutRecord = findOrCreateWorkoutRecord(date, account);
        Weight savedWeight = weightRepository.save(Weight.of(createWeightDto.getName(), createWeightDto.getBody(), workoutRecord));
        createWeightDto.getWeightSetList().forEach(weightSetDto ->
                weightSetRepository.save(WeightSet.of(weightSetDto.getSett(), weightSetDto.getReps(), weightSetDto.getSetWeight(), savedWeight)));
        return WeightCreateResultDto.createDto(true, savedWeight, date);
    }

    @Transactional
    public WorkoutDeleteResultDto deleteWeightRecord(Long accountId, Long weightId) {
        Account account = getAccount(accountId);
        Weight weight = weightRepository.findByIdWithRecord(weightId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WEIGHT_RECORD));
        checkWeightOwnerOrNot(account, weight);
        weightSetRepository.findAllByWeight(weight).forEach(weightSetRepository::delete);
        weightRepository.delete(weight);
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

    private void checkWeightOwnerOrNot(Account account, Weight weight) {
        if (!weight.getWorkoutRecord().getAccount().getId().equals(account.getId())) {
            throw new IllegalWorkoutArgumentException(ErrorCode.DELETE_RECORD_MYSELF_ONLY);
        }
    }

    public WorkoutRecord getWorkoutRecord(Long recordId) {
        return workoutRecordRepository.findById(recordId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WORKOUT_RECORD));
    }
}