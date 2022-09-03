package wit.shortterm1.kkoowoon.domain.etc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wit.shortterm1.kkoowoon.domain.avatar.persist.Avatar;
import wit.shortterm1.kkoowoon.domain.avatar.repository.AvatarRepository;
import wit.shortterm1.kkoowoon.domain.avatar.service.AvatarService;
import wit.shortterm1.kkoowoon.domain.race.dto.request.RaceCreateDto;
import wit.shortterm1.kkoowoon.domain.race.dto.response.RaceCreateResultDto;
import wit.shortterm1.kkoowoon.domain.race.service.RaceService;
import wit.shortterm1.kkoowoon.domain.workout.dto.WeightSetDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateDietDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateWeightDto;
import wit.shortterm1.kkoowoon.domain.workout.persist.WeightSet;
import wit.shortterm1.kkoowoon.domain.workout.repository.WeightRepository;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.workout.persist.Weight;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;
import wit.shortterm1.kkoowoon.domain.workout.repository.WorkoutRecordRepository;
import wit.shortterm1.kkoowoon.domain.workout.service.WorkoutRecordService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postConstruct")
public class PostConstructController {

    private final AccountRepository accountRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarService avatarService;
    private final WorkoutRecordRepository workoutRecordRepository;
    private final WeightRepository weightRepository;
    private final WorkoutRecordService workoutRecordService;
    private final RaceService raceService;

//    @PostConstruct
    public void postConstruct() {
//        Account sampleAccount1 = Account.of("kakao1", "닉네임1", 30, 190.0, 86.4, 1, 0, 15.4, 30.0, "샘플 유저1", "sample.1url");
//        Account sampleAccount2 = Account.of("kakao2", "닉네임2", 31, 160.0, 80.1, 3, 1342, 12.4, 20.0, "샘플 유저2", "sample2.url");
//        Account sampleAccount3 = Account.of("kakao3", "닉네임3", 20, 170.0, 67.1, 2, 560, 10.4, 25.0, "샘플 유저3", "sample3.url");
//        accountRepository.save(sampleAccount1);
//        accountRepository.save(sampleAccount2);
//        accountRepository.save(sampleAccount3);
//
//        Avatar sampleAvatar = Avatar.of(1, "레벨 1 아바타", "https://avatarUrl.com");
//        avatarRepository.save(sampleAvatar);
//
//        avatarService.earnNewAvatar(sampleAccount1, sampleAvatar);
//        avatarService.earnNewAvatar(sampleAccount2, sampleAvatar);
//        avatarService.earnNewAvatar(sampleAccount3, sampleAvatar);
//
//        // 레이스 생성
//        RaceCreateDto raceCreateDto = RaceCreateDto.createDto(LocalDate.now().minusDays(1), LocalDate.now().plusDays(10), "근육 만들기 레이스", "12345", "#웨이트", sampleAccount1.getNickname(), "레이스 소개입니다.");
//        RaceCreateResultDto raceCreateResultDto = raceService.createRace(new HttpServletRequest(), raceCreateDto);
//        String raceCode = raceCreateResultDto.getRaceCode();
//
//        // 레이스 참가
//        raceService.participateRace(sampleAccount2.getId(), raceCode, "12345");
//        raceService.participateRace(sampleAccount3.getId(), raceCode, "12345");
//
//        // 운동 생성
//        List<WeightSetDto> weightSetDtoList = new ArrayList<>();
//        WeightSet weightSet1 = WeightSet.of(1, 8, 45.0, null);
//        WeightSet weightSet2 = WeightSet.of(2, 8, 50.0, null);
//        WeightSet weightSet3 = WeightSet.of(3, 8, 55.0, null);
//        weightSetDtoList.add(WeightSetDto.createDto(weightSet1));
//        weightSetDtoList.add(WeightSetDto.createDto(weightSet2));
//        weightSetDtoList.add(WeightSetDto.createDto(weightSet3));
//        CreateWeightDto createWeightDto = CreateWeightDto.createDto("벤치 프레스", "가슴", weightSetDtoList);
//        workoutRecordService.createWeightRecord(sampleAccount2.getId(), raceCreateResultDto.getRaceId(), LocalDate.now(), createWeightDto);


    }
}
