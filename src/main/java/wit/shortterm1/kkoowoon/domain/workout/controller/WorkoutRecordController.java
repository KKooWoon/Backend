package wit.shortterm1.kkoowoon.domain.workout.controller;

import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.*;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.*;
import wit.shortterm1.kkoowoon.domain.workout.service.WorkoutRecordService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record")
@Api(tags = "운동 기록 관련 API")
public class WorkoutRecordController {

    private final WorkoutRecordService workoutRecordService;

    @GetMapping
    @ApiOperation(value = "특정 유저의 특정 운동 기록을 가져오는 API", notes = "회원 ID와 레이스 ID, 날짜를 통해 해당 운동 기록을 가져오는 API")
    public ResponseEntity<WorkoutRecordDto> getOneRaceRecord(
            @ApiParam(value = "회원 ID", required = true, example = "2")
            @RequestParam("accountId") Long accountId,
            @ApiParam(value = "레이스 ID", required = true, example = "2")
            @RequestParam("raceId") Long raceId,
            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        return new ResponseEntity<>(workoutRecordService.findWorkoutRecord(accountId, raceId, date), HttpStatus.OK);
    }

    @GetMapping("/recent")
    @ApiOperation(value = "특정 유저의 최근 운동 기록 가져오기", notes = "회원 ID를 통해 해당 회원의 최근 운동기록 리스트를 가져오는 API")
    public ResponseEntity<RecentRecordDto> getRecentRecords(
            @ApiParam(value = "회원 ID", required = true, example = "2")
            @RequestParam("accountId") Long accountId) {
        return new ResponseEntity<>(workoutRecordService.findRecentRecords(accountId), HttpStatus.OK);
    }

    @GetMapping("/race")
    @ApiOperation(value = "특정 레이스의 모든 레이스 기록을 일별로 가져오기", notes = "레이스 ID를 통해 해당 레이스의 모든 운동기록을 일별로 가져오는 API")
    public ResponseEntity<RaceAllRecordDto> getRaceAllRecords(
            @ApiParam(value = "레이스 ID", required = true, example = "2")
            @RequestParam("raceId") Long raceId) {
        return new ResponseEntity<>(workoutRecordService.findRaceAllRecords(raceId), HttpStatus.OK);
    }

    @GetMapping("/month/all")
    @ApiOperation(value = "특정 달의 모든 레이스의 모든 운동 기록을 가져오기", notes = "유산소 생성 DTO와 날짜를 기반으로 유산소 운동을 추가하는 API")
    public ResponseEntity<OneMonthAllRecordDto> getMonthAllRecord(
            @ApiParam(value = "회원 ID", required = true, example = "2")
            @RequestParam("accountId") Long accountId,
            @ApiParam(value = "년도", required = true, example = "2022")
            @RequestParam("year") int year,
            @ApiParam(value = "월", required = true, example = "8")
            @RequestParam("month") int month) {
        return new ResponseEntity<>(workoutRecordService.findOneMonthAllRecord(accountId, year, month), HttpStatus.OK);
    }

    @GetMapping("/month/one")
    @ApiOperation(value = "특정 달의 특정 레이스의 모든 운동 기록을 가져오기", notes = "유산소 생성 DTO와 날짜를 기반으로 유산소 운동을 추가하는 API")
    public ResponseEntity<OneMonthRecordDto> getMonthOneRecord(
            @ApiParam(value = "회원 ID", required = true, example = "2")
            @RequestParam("accountId") @NotNull Long accountId,
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam("raceId") @NotNull Long raceId,
            @ApiParam(value = "년도", required = true, example = "2022")
            @RequestParam("year") @NotNull int year,
            @ApiParam(value = "월", required = true, example = "7")
            @RequestParam("month") @NotNull int month) {
        return new ResponseEntity<>(workoutRecordService.findOneMonthOneRecord(accountId, raceId, year, month), HttpStatus.OK);
    }

    @PostMapping("/cardio")
    @ApiOperation(value = "유산소 운동 새로 추가하는 API", notes = "유산소 생성 DTO와 날짜를 기반으로 유산소 운동을 추가하는 API")
    public ResponseEntity<CardioCreateResultDto> createCardioRecord(
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam("raceId") Long raceId,
            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @ApiParam(value = "유산소 생성 DTO", required = true, example = "문서 참고")
            @RequestBody CreateCardioDto createCardioDto, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.createCardioRecord(request, raceId, date, createCardioDto), HttpStatus.OK);
    }

    @PutMapping("/cardio")
    @ApiOperation(value = "유산소 운동을 수정하는 API", notes = "유산소 수정 DTO와 날짜를 기반으로 유산소 운동을 수정하는 API")
    public ResponseEntity<CardioUpdateResultDto> updateCardioRecord(
            @ApiParam(value = "유산소 운동 ID", required = true, example = "4")
            @RequestParam @NotNull Long cardioId,
            @ApiParam(value = "유산소 수정 DTO", required = true, example = "문서 참고")
            @RequestBody @NotNull UpdateCardioDto updateCardioDto, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.updateCardioRecord(request, cardioId, updateCardioDto), HttpStatus.OK);
    }


    @DeleteMapping("/cardio")
    @ApiOperation(value = "유산소 운동 삭제하는 API", notes = "유산소 운동 ID로 삭제하는 API")
    public ResponseEntity<WorkoutDeleteResultDto> deleteCardioRecord(
            @ApiParam(value = "유산소 운동 ID", required = true, example = "4")
            @RequestParam Long cardioId, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.deleteCardioRecord(request, cardioId), HttpStatus.OK);
    }

    @PostMapping("/diet")
    @ApiOperation(value = "식단을 새로 추가하는 API", notes = "식단 생성 DTO와 날짜를 기반으로 식단을 추가하는 API")
    public ResponseEntity<DietCreateResultDto> createDietRecord(
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam("raceId") Long raceId,
            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @ApiParam(value = "식단 생성 DTO", required = true, example = "문서 참고")
            @RequestBody CreateDietDto createDietDto, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.createDietRecord(request, raceId, date, createDietDto), HttpStatus.OK);
    }

    @PutMapping("/diet")
    @ApiOperation(value = "식단을 수정하는 API", notes = "식단 수정 DTO와 날짜를 기반으로 식단을 수정하는 API")
    public ResponseEntity<DietUpdateResultDto> updateDietRecord(
            @ApiParam(value = "식단 ID", required = true, example = "4")
            @RequestParam @NotNull Long dietId,
            @ApiParam(value = "식단 수정 DTO", required = true, example = "문서 참고")
            @RequestBody @NotNull UpdateDietDto updateDietDto, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.updateDietRecord(request, dietId, updateDietDto), HttpStatus.OK);
    }

    @DeleteMapping("/diet")
    @ApiOperation(value = "식단을 삭제하는 API", notes = "식단 ID로 삭제하는 API")
    public ResponseEntity<WorkoutDeleteResultDto> deleteDietRecord(
            @ApiParam(value = "식단 ID", required = true, example = "4")
            @RequestParam Long dietId, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.deleteDietRecord(request, dietId), HttpStatus.OK);
    }

    @PostMapping("/weight")
    @ApiOperation(value = "웨이트를 새로 추가하는 API", notes = "웨이트 생성 DTO와 날짜를 기반으로 웨이트를 추가하는 API")
    public ResponseEntity<WeightCreateResultDto> createWeightRecord(
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam("raceId") Long raceId,
            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @ApiParam(value = "웨이트 생성 DTO", required = true, example = "문서 참고")
            @RequestBody CreateWeightDto createWeightDto, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.createWeightRecord(request, raceId, date, createWeightDto), HttpStatus.OK);
    }

    @PutMapping("/weight")
    @ApiOperation(value = "웨이트 운동을 수정하는 API", notes = "웨이트 수정 DTO와 날짜를 기반으로 웨이트 운동을 수정하는 API")
    public ResponseEntity<WeightUpdateResultDto> updateWeightRecord(
            @ApiParam(value = "웨이트 ID", required = true, example = "4")
            @RequestParam @NotNull Long weightId,
            @ApiParam(value = "웨이트 수정 DTO", required = true, example = "문서 참고")
            @RequestBody @NotNull UpdateWeightDto updateWeightDto, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.updateWeightRecord(request, weightId, updateWeightDto), HttpStatus.OK);
    }

    @DeleteMapping("/weight")
    @ApiOperation(value = "웨이트 기록을 삭제하는 API", notes = "웨이트 ID로 삭제하는 API")
    public ResponseEntity<WorkoutDeleteResultDto> deleteWeightRecord(
            @ApiParam(value = "웨이트 ID", required = true, example = "4")
            @RequestParam Long weightId, HttpServletRequest request) {
        return new ResponseEntity<>(workoutRecordService.deleteWeightRecord(request, weightId), HttpStatus.OK);
    }

    /**
     * 1. # 유산소 운동 삭제(유산소, 웨이트, 식단 삭제할 때 하나 남은 거 삭제면 기록도 삭제하자)
     * 2. 웨이트 추가
     * 3. 웨이트 삭제
     * 4. # 식단 추가
     * 5. # 식단 삭제
     * 6. 기록 자체 추가
     * 7. 기록 자체 삭제(?)
     *
     */

//    @PostMapping("/weight")
//    @ApiOperation(value = "유산소 운동 새로 추가하는 API", notes = "유산소 생성 DTO와 날짜를 기반으로 유산소 운동을 추가하는 API")
//    public ResponseEntity<CardioCreateResultDto> createWeightRecord(
//            @ApiParam(value = "닉네임", required = true, example = "꾸운닉123")
//            @RequestParam String nickname,
//            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
//            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
//            @ApiParam(value = "유산소 생성 DTO", required = true, example = "문서 참고")
//            @RequestBody CreateCardioDto createCardioDto) {
//        return new ResponseEntity<>(workoutRecordService.createCardioRecord(nickname, date, createCardioDto), HttpStatus.OK);
//    }
//
//    @PostMapping("/diet")
//    @ApiOperation(value = "유산소 운동 새로 추가하는 API", notes = "유산소 생성 DTO와 날짜를 기반으로 유산소 운동을 추가하는 API")
//    public ResponseEntity<CardioCreateResultDto> createDietRecord(
//            @ApiParam(value = "닉네임", required = true, example = "꾸운닉123")
//            @RequestParam String nickname,
//            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
//            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
//            @ApiParam(value = "유산소 생성 DTO", required = true, example = "문서 참고")
//            @RequestBody CreateCardioDto createCardioDto) {
//        return new ResponseEntity<>(workoutRecordService.createCardioRecord(nickname, date, createCardioDto), HttpStatus.OK);
//    }

//    @PostMapping
//    @ApiOperation(value = "팔로우를 새로 하는 API", notes = "소스ID -> 타겟ID로 팔로우가 되는 API")
//    public ResponseEntity<FollowResultDto> createRecord(
//            @ApiParam(value = "팔로우하는 소스 닉네임(나 자신)", required = true, example = "꾸운닉123")
//            @RequestParam String sourceNickname,
//            @ApiParam(value = "팔로우 대상 닉네임", required = true, example = "인기쟁이333")
//            @RequestParam String targetNickname) {
//        return new ResponseEntity<>(workoutRecordService.createRecord(sourceNickname, targetNickname), HttpStatus.OK);
//    }
}