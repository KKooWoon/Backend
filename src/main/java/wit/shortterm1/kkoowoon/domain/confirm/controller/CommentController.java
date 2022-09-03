package wit.shortterm1.kkoowoon.domain.confirm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.confirm.dto.request.CommentCreateDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.request.CommentUpdateDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.*;
import wit.shortterm1.kkoowoon.domain.confirm.service.CommentService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@Api(tags = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ApiOperation(value = "특정 운동 인증에 댓글 달기", notes = "인증 ID와 사용자 ID를 이용해 댓글을 생성하는 API")
    public ResponseEntity<CommentCreateResultDto> createComment(
            @ApiParam(value = "운동 인증 ID", required = true, example = "2")
            @RequestParam("confirmId") Long confirmId,
            @ApiParam(value = "코멘트 생성 DTO", required = true, example = "문서 참고")
            @RequestBody CommentCreateDto commentCreateDto, HttpServletRequest request) {
        return new ResponseEntity<>(commentService.createComment(request, confirmId, commentCreateDto), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "댓글 삭제하기", notes = "댓글 ID를 이용해 댓글을 삭제하는 API")
    public ResponseEntity<CommentDeleteResultDto> deleteComment(
            @ApiParam(value = "댓글 ID", required = true, example = "1")
            @RequestParam("commentId") Long commentId, HttpServletRequest request) {
        return new ResponseEntity<>(commentService.deleteComment(request, commentId), HttpStatus.OK);
    }

    @PutMapping
    @ApiOperation(value = "댓글 수정하기", notes = "댓글 ID및 수정 DTO를 이용해 댓글을 수정하는 API")
    public ResponseEntity<CommentUpdateResultDto> updateComment(
            @ApiParam(value = "댓글 ID", required = true, example = "1")
            @RequestParam("commentId") Long commentId,
            @ApiParam(value = "코멘트 수정 DTO", required = true, example = "문서 참고")
            @RequestBody CommentUpdateDto commentUpdateDto, HttpServletRequest request) {
        return new ResponseEntity<>(commentService.updateComment(request, commentId, commentUpdateDto), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "특정 댓글 가져오기", notes = "댓글 ID를 이용해 댓글을 가져오는 API")
    public ResponseEntity<CommentInfoDto> getComment(
            @ApiParam(value = "댓글 ID", required = true, example = "3")
            @RequestParam("commentId") Long commentId) {
        return new ResponseEntity<>(commentService.findComment(commentId), HttpStatus.OK);
    }

    @GetMapping("/list")
    @ApiOperation(value = "특정 인증글의 댓글들 가져오기", notes = "인증 ID를 이용해 댓글 리스트를 가져오는 API")
    public ResponseEntity<CommentInfoListDto> getCommentList(
            @ApiParam(value = "운동 인증 ID", required = true, example = "3")
            @RequestParam("confirmId") Long confirmId) {
        return new ResponseEntity<>(commentService.findCommentList(confirmId), HttpStatus.OK);
    }

}
