package wit.shortterm1.kkoowoon.domain.confirm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wit.shortterm1.kkoowoon.domain.confirm.dto.request.ConfirmCreateDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.*;
import wit.shortterm1.kkoowoon.domain.confirm.service.ConfirmService;
import wit.shortterm1.kkoowoon.domain.etc.service.ImageService;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirm")
@Api(tags = "운동 인증 관련 API")
public class ConfirmController {

    private final ConfirmService confirmService;

    @GetMapping("/info")
    @ApiOperation(value = "인증글 가져오기(댓글 포함)", notes = "인증 ID를 통해 인증글을 가져오는 API")
    public ResponseEntity<ConfirmInfoDto> getConfirmation(
            @ApiParam(value = "운동 인증 ID", required = true, example = "1")
            @RequestParam("confirmId") Long confirmId) {
        return new ResponseEntity<>(confirmService.getConfirmation(confirmId), HttpStatus.OK);
    }

    @GetMapping("/image")
    @ApiOperation(value = "인증 이미지 가져오기", notes = "인증 ID를 통해 인증 이미지를 가져오는 API")
    public ResponseEntity<byte[]> getConfirmImages(
            @ApiParam(value = "운동 인증 ID", required = true, example = "1")
            @RequestParam("confirmId") Long confirmId) {
        ImageDto imageDto = confirmService.getImages(confirmId);
        return new ResponseEntity<>(imageDto.getResource(), imageDto.getHeaders(), HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @ApiOperation(value = "운동 기록을 인증", notes = "운동 기록 ID와 레이스 ID를 이용해 인증을 진행하는 API")
    public ResponseEntity<ConfirmCreateResultDto> createConfirmation(
            @ApiParam(value = "운동 기록 ID", required = true, example = "3")
            @RequestParam("recordId") Long recordId,
            @ApiParam(value = "이미지 파일", required = true, example = "imageFile")
            @RequestPart("file") MultipartFile imageFile,
            @ApiParam(value = "인증 생성 DTO", required = true, example = "RaceCreateDto 참조")
            @RequestPart ConfirmCreateDto confirmCreateDto, HttpServletRequest request) {
        return new ResponseEntity<>(confirmService.createConfirmation(request, recordId, imageFile, confirmCreateDto), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "운동 인증 기록을 삭제", notes = "운동 인증 ID를 이용해 삭제를 진행하는 API")
    public ResponseEntity<ConfirmDeleteResultDto> deleteConfirmation(
            @ApiParam(value = "운동 인증 ID", required = true, example = "1")
            @RequestParam("confirmId") Long confirmId, HttpServletRequest request) {
        return new ResponseEntity<>(confirmService.deleteConfirmation(request, confirmId), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "해당 레이스에 해당 운동 기록이 인증되었는지 확인", notes = "운동 기록 ID와 레이스 ID를 이용해 인증 여부를 확인해주고 인증됐다면 인증 ID를 리턴해주는 API")
    public ResponseEntity<ConfirmCheckResultDto> isConfirmed(
            @ApiParam(value = "운동 기록 ID", required = true, example = "3")
            @RequestParam("recordId") Long recordId) {
        return new ResponseEntity<>(confirmService.isConfirmed(recordId), HttpStatus.OK);
    }
}