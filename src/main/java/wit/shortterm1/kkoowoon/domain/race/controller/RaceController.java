package wit.shortterm1.kkoowoon.domain.race.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.race.dto.request.RaceCreateDto;
import wit.shortterm1.kkoowoon.domain.race.dto.request.RaceUpdateDto;
import wit.shortterm1.kkoowoon.domain.race.dto.response.*;
import wit.shortterm1.kkoowoon.domain.race.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Api(tags = "레이스 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/race")
public class RaceController {

    private final RaceService raceService;

    @GetMapping
    @ApiOperation(value = "레이스 조회", notes = "레이스 ID를 통해 레이스 정보를 조회하는 API")
    public ResponseEntity<RaceInfoWithParticipateDto> getRaceInfo(
            @ApiParam(value = "레이스 ID", required = true, example = "2")
            @RequestParam Long raceId, HttpServletRequest request) {
        return new ResponseEntity<>(raceService.getRaceInfoWithParticipateOrNot(request, raceId), HttpStatus.OK);
    }

    @GetMapping("/name-search")
    @ApiOperation(value = "이름으로 레이스 조회", notes = "해당 이름을 가진 레이스들을 조회하는 API")
    public ResponseEntity<CurrentRaceListDto> findRaceListWithName(
            @ApiParam(value = "레이스 이름", required = true, example = "다이어트 레이스")
            @RequestParam String name) {
        return new ResponseEntity<>(raceService.findRaceListWithName(name), HttpStatus.OK);
    }

    @GetMapping("/tag-search")
    @ApiOperation(value = "태그로 레이스 조회", notes = "해당 태그를 가진 레이스들을 조회하는 API")
    public ResponseEntity<CurrentRaceListDto> findRaceListWithTag(
            @ApiParam(value = "태그 이름", required = true, example = "#다이어트")
            @RequestParam String tag) {
        return new ResponseEntity<>(raceService.findRaceListWithTag(tag), HttpStatus.OK);
    }

    @PostMapping("/create")
    @ApiOperation(value = "레이스를 새로 생성", notes = "레이스 생성 DTO를 토대로 새로운 레이스를 생성하는 API")
    public ResponseEntity<RaceCreateResultDto> createRace(
            @ApiParam(value = "레이스 생성 DTO", required = true, example = "RaceCreateDto 참조")
            @RequestBody RaceCreateDto raceCreateDto, HttpServletRequest request) {
        return new ResponseEntity<>(raceService.createRace(request, raceCreateDto), HttpStatus.OK);
    }

    @PostMapping("/participate")
    @ApiOperation(value = "레이스를 참가함", notes = "참여자 닉네임과 레이스 코드를 토대로 레이스에 새로 참여하는 API")
    public ResponseEntity<RaceParticipateResultDto> participateRace(
            @ApiParam(value = "레이스 코드", required = true, example = "AHDJIK")
            @RequestParam String raceCode,
            @ApiParam(value = "레이스 비밀번호", required = true, example = "kkoowoon123!")
            @RequestParam String racePassword, HttpServletRequest request) {
        return new ResponseEntity<>(raceService.participateRace(request, raceCode, racePassword), HttpStatus.OK);
    }

    @DeleteMapping("/participate")
    @ApiOperation(value = "레이스 참가를 취소", notes = "참여자 닉네임과 레이스 코드를 이용해 참가를 취소하는 API")
    public ResponseEntity<RaceLeaveResultDto> leaveRace(
            @ApiParam(value = "레이스 코드", required = true, example = "AHDJIK")
            @RequestParam String raceCode, HttpServletRequest request) {
        return new ResponseEntity<>(raceService.leaveRace(request, raceCode), HttpStatus.OK);
    }

    @DeleteMapping("/create")
    @ApiOperation(value = "생성한 레이스를 삭제", notes = "사용자 id값을 통해 획득한 아바타 리스트를 가져오는 API")
    public ResponseEntity<RaceDeleteResultDto> deleteRace(
            @ApiParam(value = "레이스 코드", required = true, example = "AHDJIK")
            @RequestParam String raceCode,
            @ApiParam(value = "레이스 비밀번호", required = true, example = "kkoowoon123!")
            @RequestParam String racePassword, HttpServletRequest request) {
        return new ResponseEntity<>(raceService.deleteRace(request, raceCode, racePassword), HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "레이스 수정", notes = "사용자 ID값과 수정 DTO를 통해 레이스 정보를 수정하는 API")
    public ResponseEntity<RaceUpdateResultDto> updateRace(
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam Long raceId,
            @ApiParam(value = "레이스 수정 DTO", required = true, example = "RaceCreateDto 참조")
            @RequestBody RaceUpdateDto raceUpdateDto, HttpServletRequest request) {
        return new ResponseEntity<>(raceService.updateRace(request, raceId, raceUpdateDto), HttpStatus.OK);
    }

    @GetMapping("/participate/info/current")
    @ApiOperation(value = "사용자가 참여한 '진행중인 레이스' 조회", notes = "사용자가 참여한 '진행중인 레이스' 조회하는 API")
    public ResponseEntity<CurrentRaceListDto> getCurrentRaceList(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "2")
            @RequestParam Long accountId) {
        return new ResponseEntity<>(raceService.findCurrentRaceList(accountId), HttpStatus.OK);
    }

    @GetMapping("/participate/info/current/confirm")
    @ApiOperation(value = "사용자가 참여한 '진행중인 레이스' + 완료 여부까지 조회", notes = "사용자가 참여한 '진행중인 레이스'를 완료 여부까지 조회하는 API")
    public ResponseEntity<CurrentRaceWithConfirmListDto> getCurrentRaceListWithConfirm(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "2")
            @RequestParam Long accountId,
            @ApiParam(value = "날짜(오늘 값 주면 됨)", required = true, example = "2022-07-21")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        return new ResponseEntity<>(raceService.findCurrentRaceListWithConfirm(accountId, date), HttpStatus.OK);
    }

    @GetMapping("/participate/info/past")
    @ApiOperation(value = "사용자가 참여헀던 '완료된 레이스' 조회", notes = "사용자가 참여헀던 '완료된 레이스' 조회하는 API")
    public ResponseEntity<PastRaceListDto> getPastRaceList(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "2")
            @RequestParam Long accountId,
            @ApiParam(value = "날짜(오늘 값 주면 됨)", required = true, example = "2022-07-21")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        return new ResponseEntity<>(raceService.findPastRaceList(accountId, date), HttpStatus.OK);
    }

    @GetMapping("/participate/info/all")
    @ApiOperation(value = "사용자가 참여한 '모든 레이스(진행중 + 완료)' 조회", notes = "사용자가 참여한 '모든 레이스(진행중 + 완료)' 조회하는 API")
    public ResponseEntity<AllRaceListDto> getAllRaceList(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "2")
            @RequestParam Long accountId,
            @ApiParam(value = "날짜(오늘 값 주면 됨)", required = true, example = "2022-07-21")
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        return new ResponseEntity<>(raceService.findAllRaceList(accountId, date), HttpStatus.OK);
    }

    @GetMapping("/ranking")
    @ApiOperation(value = "레이스 랭킹 리스트 조회", notes = "레이스 ID로 특정 레이스의 사용자들을 스코어 순으로 나열한 리스트 API")
    public ResponseEntity<RaceRankingDto> getRanking(
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam Long raceId) {
        return new ResponseEntity<>(raceService.getRanking(raceId), HttpStatus.OK);
    }

    @GetMapping("/user-list")
    @ApiOperation(value = "해당 레이스의 참여자 리스트 조회", notes = "레이스 ID로 해당 레이스의 참여자 리스트를 리턴하는 API")
    public ResponseEntity<RaceParticipantsDto> getParticipants(
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam Long raceId) {
        return new ResponseEntity<>(raceService.getParticipants(raceId), HttpStatus.OK);
    }



//    @GetMapping("/createList")
//    @ApiOperation(value = "사용자가 생성한 모든 레이스 조회", notes = "사용자 id값을 통해 획득한 아바타 리스트를 가져오는 API")
//    public ResponseEntity<RaceParticipateResultDto> getRaceList(
//            @ApiParam(value = "레이스 참여자 닉네임", required = true, example = "꾸운닉123")
//            @RequestParam String ownerNickname,
//            @ApiParam(value = "레이스 코드", required = true, example = "AHDJIK")
//            @RequestBody String raceCode,
//            @ApiParam(value = "레이스 비밀번호", required = true, example = "kkoowoon123!")
//            @RequestBody String racePassword) {
//        return new ResponseEntity<>(raceService.participateRace(ownerNickname, raceCode, racePassword), HttpStatus.OK);
//    }

    // 1. 레이스 비번 변경
    // 2. 레이스 방장 변경

}
