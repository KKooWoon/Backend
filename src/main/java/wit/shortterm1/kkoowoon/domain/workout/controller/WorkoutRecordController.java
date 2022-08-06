package wit.shortterm1.kkoowoon.domain.workout.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateCardioDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.CreateDietDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.CardioCreateResultDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.DietCreateResultDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.WorkoutDeleteResultDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.WorkoutRecordDto;
import wit.shortterm1.kkoowoon.domain.workout.service.WorkoutRecordService;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record")
@Api(tags = "운동 기록 관련 API")
public class WorkoutRecordController {

    private final WorkoutRecordService workoutRecordService;

    @GetMapping
    @ApiOperation(value = "운동 기록을 가져오는 API", notes = "유산소 생성 DTO와 날짜를 기반으로 유산소 운동을 추가하는 API")
    public ResponseEntity<WorkoutRecordDto> getRecord(
            @ApiParam(value = "닉네임", required = true, example = "꾸운닉123")
            @RequestParam String nickname,
            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        return new ResponseEntity<>(workoutRecordService.findWorkoutRecord(nickname, date), HttpStatus.OK);
    }

    @PostMapping("/cardio")
    @ApiOperation(value = "유산소 운동 새로 추가하는 API", notes = "유산소 생성 DTO와 날짜를 기반으로 유산소 운동을 추가하는 API")
    public ResponseEntity<CardioCreateResultDto> createCardioRecord(
            @ApiParam(value = "닉네임", required = true, example = "꾸운닉123")
            @RequestParam String nickname,
            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @ApiParam(value = "유산소 생성 DTO", required = true, example = "문서 참고")
            @RequestBody CreateCardioDto createCardioDto) {
        return new ResponseEntity<>(workoutRecordService.createCardioRecord(nickname, date, createCardioDto), HttpStatus.OK);
    }

    @DeleteMapping("/cardio")
    @ApiOperation(value = "유산소 운동 삭제하는 API", notes = "유산소 운동 ID로 삭제하는 API")
    public ResponseEntity<WorkoutDeleteResultDto> deleteCardioRecord(
            @ApiParam(value = "닉네임", required = true, example = "꾸운닉123")
            @RequestParam String nickname,
            @ApiParam(value = "유산소 운동 ID", required = true, example = "4")
            @RequestParam Long cardioId) {
        return new ResponseEntity<>(workoutRecordService.deleteCardioRecord(nickname, cardioId), HttpStatus.OK);
    }

    @PostMapping("/diet")
    @ApiOperation(value = "식단을 새로 추가하는 API", notes = "식단 생성 DTO와 날짜를 기반으로 식단을 추가하는 API")
    public ResponseEntity<DietCreateResultDto> createDietRecord(
            @ApiParam(value = "닉네임", required = true, example = "꾸운닉123")
            @RequestParam String nickname,
            @ApiParam(value = "날짜", required = true, example = "2022-07-30")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date,
            @ApiParam(value = "식단 생성 DTO", required = true, example = "문서 참고")
            @RequestBody CreateDietDto createDietDto) {
        return new ResponseEntity<>(workoutRecordService.createDietRecord(nickname, date, createDietDto), HttpStatus.OK);
    }

    @DeleteMapping("/diet")
    @ApiOperation(value = "식단을 삭제하는 API", notes = "식단 ID로 삭제하는 API")
    public ResponseEntity<WorkoutDeleteResultDto> deleteDietRecord(
            @ApiParam(value = "닉네임", required = true, example = "꾸운닉123")
            @RequestParam String nickname,
            @ApiParam(value = "식단 ID", required = true, example = "4")
            @RequestParam Long dietId) {
        return new ResponseEntity<>(workoutRecordService.deleteDietRecord(nickname, dietId), HttpStatus.OK);
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
