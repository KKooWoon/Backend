package wit.shortterm1.kkoowoon.domain.race.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.race.dto.request.RaceCreateDto;
import wit.shortterm1.kkoowoon.domain.race.dto.response.*;
import wit.shortterm1.kkoowoon.domain.race.service.RaceService;

@Api(tags = "레이스 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/race")
public class RaceController {

    private final RaceService raceService;

    @PostMapping("/create")
    @ApiOperation(value = "레이스를 새로 생성", notes = "레이스 생성 DTO를 토대로 새로운 레이스를 생성하는 API")
    public ResponseEntity<RaceCreateResultDto> createRace(
            @ApiParam(value = "레이스 주인 ID", required = true, example = "2")
            @RequestParam Long ownerId,
            @ApiParam(value = "레이스 생성 DTO", required = true, example = "RaceCreateDto 참조")
            @RequestBody RaceCreateDto raceCreateDto) {
        return new ResponseEntity<>(raceService.createRace(ownerId, raceCreateDto), HttpStatus.OK);
    }

    @PostMapping("/participate")
    @ApiOperation(value = "레이스를 참가함", notes = "참여자 닉네임과 레이스 코드를 토대로 레이스에 새로 참여하는 API")
    public ResponseEntity<RaceParticipateResultDto> participateRace(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "3")
            @RequestParam Long accountId,
            @ApiParam(value = "레이스 코드", required = true, example = "AHDJIK")
            @RequestParam String raceCode,
            @ApiParam(value = "레이스 비밀번호", required = true, example = "kkoowoon123!")
            @RequestParam String racePassword) {
        return new ResponseEntity<>(raceService.participateRace(accountId, raceCode, racePassword), HttpStatus.OK);
    }

    @DeleteMapping("/participate")
    @ApiOperation(value = "레이스 참가를 취소", notes = "참여자 닉네임과 레이스 코드를 이용해 참가를 취소하는 API")
    public ResponseEntity<RaceLeaveResultDto> leaveRace(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "2")
            @RequestParam Long accountId,
            @ApiParam(value = "레이스 코드", required = true, example = "AHDJIK")
            @RequestParam String raceCode) {
        return new ResponseEntity<>(raceService.leaveRace(accountId, raceCode), HttpStatus.OK);
    }

    @DeleteMapping("/create")
    @ApiOperation(value = "생성한 레이스를 삭제", notes = "사용자 id값을 통해 획득한 아바타 리스트를 가져오는 API")
    public ResponseEntity<RaceDeleteResultDto> deleteRace(
            @ApiParam(value = "레이스 주인 ID", required = true, example = "2")
            @RequestParam Long ownerId,
            @ApiParam(value = "레이스 코드", required = true, example = "AHDJIK")
            @RequestBody String raceCode,
            @ApiParam(value = "레이스 비밀번호", required = true, example = "kkoowoon123!")
            @RequestBody String racePassword) {
        return new ResponseEntity<>(raceService.deleteRace(ownerId, raceCode, racePassword), HttpStatus.OK);
    }

    @GetMapping("/participate/info/current")
    @ApiOperation(value = "사용자가 참여한 '진행중인 레이스' 조회", notes = "사용자가 참여한 '진행중인 레이스' 조회하는 API")
    public ResponseEntity<CurrentRaceListDto> getCurrentRaceList(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "2")
            @RequestParam Long accountId) {
        return new ResponseEntity<>(raceService.findCurrentRaceList(accountId), HttpStatus.OK);
    }

    @GetMapping("/participate/info/past")
    @ApiOperation(value = "사용자가 참여헀던 '완료된 레이스' 조회", notes = "사용자가 참여헀던 '완료된 레이스' 조회하는 API")
    public ResponseEntity<PastRaceListDto> getPastRaceList(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "2")
            @RequestParam Long accountId) {
        return new ResponseEntity<>(raceService.findPastRaceList(accountId), HttpStatus.OK);
    }

    @GetMapping("/participate/info/all")
    @ApiOperation(value = "사용자가 참여한 '모든 레이스(진행중 + 완료)' 조회", notes = "사용자가 참여한 '모든 레이스(진행중 + 완료)' 조회하는 API")
    public ResponseEntity<AllRaceListDto> getAllRaceList(
            @ApiParam(value = "레이스 참여자 ID", required = true, example = "2")
            @RequestParam Long accountId) {
        return new ResponseEntity<>(raceService.findAllRaceList(accountId), HttpStatus.OK);
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
