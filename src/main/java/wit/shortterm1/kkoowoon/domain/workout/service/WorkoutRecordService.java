package wit.shortterm1.kkoowoon.domain.workout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;
import wit.shortterm1.kkoowoon.domain.confirm.repository.ConfirmRepository;
import wit.shortterm1.kkoowoon.domain.race.exception.NoSuchParticipateException;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.race.repository.ParticipateRepository;
import wit.shortterm1.kkoowoon.domain.race.service.RaceService;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.workout.dto.CardioDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.DietDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.FoodDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.WeightDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.*;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.*;
import wit.shortterm1.kkoowoon.domain.workout.exception.IllegalWorkoutArgumentException;
import wit.shortterm1.kkoowoon.domain.workout.exception.NoSuchRecordException;
import wit.shortterm1.kkoowoon.domain.workout.persist.*;
import wit.shortterm1.kkoowoon.domain.workout.repository.*;
import wit.shortterm1.kkoowoon.global.common.jwt.JwtProvider;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private final RaceService raceService;
    private final ParticipateRepository participateRepository;
    private final JwtProvider jwtProvider;
    private final ConfirmRepository confirmRepository;

    public WorkoutRecordDto findWorkoutRecord(Long accountId, Long raceId, LocalDate date) {
        validateRecordDate(date, raceId);
        checkParticipateOrNot(accountId, raceId);
        WorkoutRecord workoutRecord = workoutRecordRepository.findByAccountNRaceNDate(accountId, raceId, date)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WORKOUT_RECORD));
       return WorkoutRecordDto.createDto(workoutRecord);
    }

    private void checkParticipateOrNot(Long accountId, Long raceId) {
        if (participateRepository.findByAccountAndRace(accountId, raceId).isEmpty()) {
            throw new NoSuchParticipateException(ErrorCode.NO_SUCH_PARTICIPATE);
        }
    }

    @Transactional
    public CardioCreateResultDto createCardioRecord(HttpServletRequest request, Long raceId, LocalDate date, CreateCardioDto createCardioDto) {
        Account account = getAccountByAccessToken(request);
        checkParticipateOrNot(account.getId(), raceId);
        validateRecordDate(date, raceId);
        WorkoutRecord workoutRecord = findOrCreateWorkoutRecord(date, raceId, account.getId());
        Cardio cardio = Cardio.of(createCardioDto.getName(), createCardioDto.getTime(), createCardioDto.getCalorie(), workoutRecord);
        cardioRepository.save(cardio);

        return CardioCreateResultDto.createDto(true, cardio.getWorkoutRecord().getId(), cardio.getId(), cardio.getCreatedAt());
    }

    private Account getAccountByAccessToken(HttpServletRequest request) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        return account;
    }

    @Transactional
    public CardioUpdateResultDto updateCardioRecord(HttpServletRequest request, Long cardioId, UpdateCardioDto updateCardioDto) {
        Account account = getAccountByAccessToken(request);
        Cardio cardio = cardioRepository.findByIdWithRecord(cardioId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_CARDIO_RECORD));
        checkCardioOwnerOrNot(account.getId(), cardio);
        cardio.updateCardio(updateCardioDto);
        return CardioUpdateResultDto.createDto(true, cardio.getWorkoutRecord().getId(), cardioId, cardio.getUpdatedAt());
    }

    @Transactional
    public WorkoutDeleteResultDto deleteCardioRecord(HttpServletRequest request, Long cardioId) {
        Account account = getAccountByAccessToken(request);
        Cardio cardio = cardioRepository.findByIdWithRecord(cardioId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_CARDIO_RECORD));
        checkCardioOwnerOrNot(account.getId(), cardio);
        cardioRepository.delete(cardio);
        return WorkoutDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    @Transactional
    public DietCreateResultDto createDietRecord(HttpServletRequest request, Long raceId, LocalDate date, CreateDietDto createDietDto) {
        Account account = getAccountByAccessToken(request);
        checkParticipateOrNot(account.getId(), raceId);
        validateRecordDate(date, raceId);
        WorkoutRecord workoutRecord = findOrCreateWorkoutRecord(date, raceId, account.getId());
        Diet savedDiet = dietRepository.save(Diet.of(createDietDto.getName(), workoutRecord));
        List<FoodDto> foodDtoList = new ArrayList<>();
        createDietDto.getFoodList().forEach((foodDto -> {
            Food savedFood = foodRepository.save(Food.of(foodDto.getName(), foodDto.getWeight(), foodDto.getCalorie(), savedDiet));
            foodDtoList.add(FoodDto.createDto(savedFood));
        }));
        return DietCreateResultDto.createDto(true, savedDiet, date, foodDtoList);
    }

    @Transactional
    public DietUpdateResultDto updateDietRecord(HttpServletRequest request, Long dietId, UpdateDietDto updateDietDto) {
        Account account = getAccountByAccessToken(request);
        Diet diet = dietRepository.findById(dietId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_DIET_RECORD));
        checkDietOwnerOrNot(account.getId(), diet);
        updateDietDto.getFoodList().forEach(foodDto -> {
            Food food = foodRepository.findById(foodDto.getFoodId())
                    .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_FOOD));
            food.updateFood(foodDto);
        });
        diet.updateDiet(updateDietDto);
        return DietUpdateResultDto.createDto(true, diet);
    }

    @Transactional
    public WorkoutDeleteResultDto deleteDietRecord(HttpServletRequest request, Long dietId) {
        Account account = getAccountByAccessToken(request);
        Diet diet = dietRepository.findByIdWithRecord(dietId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_DIET_RECORD));
        checkDietOwnerOrNot(account.getId(), diet);
        foodRepository.findAllByDiet(diet).forEach(foodRepository::delete);
        dietRepository.delete(diet);
        return WorkoutDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    @Transactional
    public WeightCreateResultDto createWeightRecord(HttpServletRequest request, Long raceId, LocalDate date, CreateWeightDto createWeightDto) {
        Account account = getAccountByAccessToken(request);
        checkParticipateOrNot(account.getId(), raceId);
        validateRecordDate(date, raceId);
        WorkoutRecord workoutRecord = findOrCreateWorkoutRecord(date, raceId, account.getId());
        Weight savedWeight = weightRepository.save(Weight.of(createWeightDto.getName(), createWeightDto.getBody(), workoutRecord));
        createWeightDto.getWeightSetList().forEach(weightSetDto ->
                weightSetRepository.save(WeightSet.of(weightSetDto.getSett(), weightSetDto.getReps(), weightSetDto.getSetWeight(), savedWeight)));
        return WeightCreateResultDto.createDto(true, savedWeight, date);
    }

    @Transactional
    public WeightUpdateResultDto updateWeightRecord(HttpServletRequest request, Long weightId, UpdateWeightDto updateWeightDto) {
        Account account = getAccountByAccessToken(request);
        Weight weight = weightRepository.findById(weightId).orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WEIGHT_RECORD));
        checkWeightOwnerOrNot(account.getId(), weight);
        updateWeightDto.getWeightSetList().forEach(weightSetDto -> {
            WeightSet weightSet = weightSetRepository.findById(weightSetDto.getSetId())
                    .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_SET));
            weightSet.updateWeightSet(weightSetDto);
        });
        weight.updateWeight(updateWeightDto);
        return WeightUpdateResultDto.createDto(true, weight);
    }

    @Transactional
    public WorkoutDeleteResultDto deleteWeightRecord(HttpServletRequest request, Long weightId) {
        Account account = getAccountByAccessToken(request);
        Weight weight = weightRepository.findByIdWithRecord(weightId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WEIGHT_RECORD));
        checkWeightOwnerOrNot(account.getId(), weight);
        weightSetRepository.findAllByWeight(weight).forEach(weightSetRepository::delete);
        weightRepository.delete(weight);
        return WorkoutDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    @Transactional
    protected WorkoutRecord findOrCreateWorkoutRecord(LocalDate date, Long raceId, Long accountId) {
        checkParticipateOrNot(accountId, raceId);
        WorkoutRecord workoutRecord = workoutRecordRepository
                .findByAccountNRaceNDate(accountId, raceId, date)
                .orElse(null);
        if (workoutRecord == null) {
            Account account = getAccount(accountId);
            Race race = raceService.getRace(raceId);
            workoutRecord = WorkoutRecord.of(false, 0.0, date, account, race);
            workoutRecordRepository.save(workoutRecord);
        }
        return workoutRecord;
    }

    public OneMonthAllRecordDto findOneMonthAllRecord(Long accountId, int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);
        Map<LocalDate, List<WorkoutRecordWithRaceDto>> map = new HashMap<>();
        OneMonthAllRecordDto oneMonthAllRecordDto = OneMonthAllRecordDto.createDto();
        participateRepository.findAllByAccountId(accountId).forEach(p -> {
            workoutRecordRepository
                    .findByAccountNRaceWithRange(accountId, p.getRace().getId(), firstDayOfMonth, lastDayOfMonth)
                    .forEach(wr -> {
                        map.computeIfAbsent(wr.getRecordDate(), k -> new ArrayList<>())
                                .add(WorkoutRecordWithRaceDto.createDto(wr));
                    });
        });
        List<LocalDate> keyList = new ArrayList<>(map.keySet());
        keyList.sort(LocalDate::compareTo);
        for (LocalDate recordDate : keyList) {
            OneMonthRecordWithDateDto dto = OneMonthRecordWithDateDto.createDto(recordDate, map.get(recordDate));
            oneMonthAllRecordDto.addOneMonthRecordDto(dto);
        }
        return oneMonthAllRecordDto;
    }

    public OneMonthRecordDto findOneMonthOneRecord(Long accountId, Long raceId, int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);

        List<WorkoutRecord> workoutRecordList = workoutRecordRepository.findByAccountNRaceWithRange(accountId, raceId, firstDayOfMonth, lastDayOfMonth);
        List<WorkoutRecordDto> workoutRecordDtoList = new ArrayList<>();
        workoutRecordList.forEach(workoutRecord -> {
            System.out.println("workoutRecord.getId() = " + workoutRecord.getId());
        });
        workoutRecordList.forEach(workoutRecord -> {
            WorkoutRecordDto workoutRecordDto = WorkoutRecordDto.createDto(workoutRecord);
            workoutRecordDtoList.add(workoutRecordDto);
        });
        return OneMonthRecordDto.createDto(raceId, workoutRecordDtoList);
    }

    private void validateRecordDate(LocalDate date, Long raceId) {
        Race race = raceService.getRace(raceId);
        if (race.getEndedAt().isBefore(date) || race.getStartedAt().isAfter(date)) {
            throw new IllegalWorkoutArgumentException(ErrorCode.OUT_OF_RACE_DURATION);
        }
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
    }

    private void checkCardioOwnerOrNot(Long accountId, Cardio cardio) {
        if (!cardio.getWorkoutRecord().getAccount().getId().equals(accountId)) {
            throw new IllegalWorkoutArgumentException(ErrorCode.DELETE_RECORD_MYSELF_ONLY);
        }
    }

    private void checkDietOwnerOrNot(Long accountId, Diet diet) {
        if (!diet.getWorkoutRecord().getAccount().getId().equals(accountId)) {
            throw new IllegalWorkoutArgumentException(ErrorCode.DELETE_RECORD_MYSELF_ONLY);
        }
    }

    private void checkWeightOwnerOrNot(Long accountId, Weight weight) {
        if (!weight.getWorkoutRecord().getAccount().getId().equals(accountId)) {
            throw new IllegalWorkoutArgumentException(ErrorCode.DELETE_RECORD_MYSELF_ONLY);
        }
    }

    public WorkoutRecord getWorkoutRecord(Long recordId) {
        return workoutRecordRepository.findById(recordId)
                .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_WORKOUT_RECORD));
    }

    public RecentRecordDto findRecentRecords(Long accountId) {
        List<SimpleWorkoutRecordDto> simpleWorkoutRecordDtoList = new ArrayList<>();
        workoutRecordRepository.findAllByAccountId(accountId)
                .stream().filter(WorkoutRecord::isConfirmed)
                .forEach(workoutRecord -> {
                    Confirm confirm = confirmRepository.findByRecord(workoutRecord)
                            .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_CONFIRMATION));
                    simpleWorkoutRecordDtoList.add(SimpleWorkoutRecordDto.createDto(workoutRecord, confirm.getDescription(), confirm.getPhotoUrl1()));
                });
        return RecentRecordDto.createDto(simpleWorkoutRecordDtoList);
    }

    public RaceAllRecordDto findRaceAllRecords(Long raceId) {
//        List<RecentRecordDto> recentRecordDtoList =
//        OneDayRaceRecordDto.createDto()
        List<WorkoutRecord> workoutRecordList = workoutRecordRepository.findAllByRaceId(raceId)
                .stream().filter(WorkoutRecord::isConfirmed).collect(Collectors.toList());
        if (workoutRecordList.isEmpty()) return RaceAllRecordDto.createDto(raceId, null);
        Map<LocalDate, ArrayList<WorkoutRecord>> map = new HashMap<>();
        workoutRecordList.forEach(w -> {
            if (map.get(w.getRecordDate()) != null) {
                map.get(w.getRecordDate()).add(w);
            } else {
                map.put(w.getRecordDate(), new ArrayList<>(List.of(w)));
            }
        });
        List<OneDayRaceRecordDto> raceAllList = new ArrayList<>();
        for (LocalDate date : map.keySet()) {
            List<SimpleWorkoutRecordDto> simpleList = new ArrayList<>();
            map.get(date).forEach(w -> {
                Confirm confirm = confirmRepository.findByRecord(w)
                        .orElseThrow(() -> new NoSuchRecordException(ErrorCode.NO_SUCH_CONFIRMATION));
                simpleList.add(SimpleWorkoutRecordDto.createDto(w, confirm.getDescription(), confirm.getPhotoUrl1()));
            });
            raceAllList.add(OneDayRaceRecordDto.createDto(date, simpleList));
        }

        return RaceAllRecordDto.createDto(raceId, raceAllList);
    }
}