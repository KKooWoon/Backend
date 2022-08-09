package wit.shortterm1.kkoowoon.domain.confirm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.confirm.dto.request.ConfirmCreateDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.ConfirmCheckResultDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.ConfirmCreateResultDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.ConfirmDeleteResultDto;
import wit.shortterm1.kkoowoon.domain.confirm.service.ConfirmService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirm")
@Api(tags = "운동 인증 관련 API")
public class ConfirmController {

    private final ConfirmService confirmService;

    @PostMapping
    @ApiOperation(value = "운동 기록을 인증", notes = "운동 기록 ID와 레이스 ID를 이용해 인증을 진행하는 API")
    public ResponseEntity<ConfirmCreateResultDto> createConfirmation(
            @ApiParam(value = "회원 ID", required = true, example = "3")
            @RequestParam("accountId") Long accountId,
            @ApiParam(value = "운동 기록 ID", required = true, example = "3")
            @RequestParam("recordId") Long recordId,
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam("raceId") Long raceId,
            @ApiParam(value = "인증 생성 DTO", required = true, example = "RaceCreateDto 참조")
            @RequestBody ConfirmCreateDto confirmCreateDto) {
        return new ResponseEntity<>(confirmService.createConfirmation(accountId, recordId, raceId, confirmCreateDto), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "운동 인증 기록을 삭제", notes = "운동 인증 ID를 이용해 삭제를 진행하는 API")
    public ResponseEntity<ConfirmDeleteResultDto> deleteConfirmation(
            @ApiParam(value = "운동 인증 ID", required = true, example = "1")
            @RequestParam("confirmId") Long confirmId) {
        return new ResponseEntity<>(confirmService.deleteConfirmation(confirmId), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "해당 레이스에 해당 운동 기록이 인증되었는지 확인", notes = "운동 기록 ID와 레이스 ID를 이용해 인증 여부를 확인해주고 인증됐다면 인증 ID를 리턴해주는 API")
    public ResponseEntity<ConfirmCheckResultDto> isConfirmed(
            @ApiParam(value = "운동 기록 ID", required = true, example = "3")
            @RequestParam("recordId") Long recordId,
            @ApiParam(value = "레이스 ID", required = true, example = "1")
            @RequestParam("raceId") Long raceId) {
        return new ResponseEntity<>(confirmService.isConfirmed(recordId, raceId), HttpStatus.OK);
    }


}