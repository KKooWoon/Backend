package wit.shortterm1.kkoowoon.domain.etc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowResultDto;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowerListDto;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowingListDto;
import wit.shortterm1.kkoowoon.domain.etc.service.FollowService;

@RestController
@Api(tags = "팔로우 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    @ApiOperation(value = "팔로우를 새로 하는 API", notes = "소스ID -> 타겟ID로 팔로우가 되는 API")
    public ResponseEntity<FollowResultDto> follow(
            @ApiParam(value = "팔로우하는 소스 ID(나 자신)", required = true, example = "3")
            @RequestParam Long sourceId,
            @ApiParam(value = "팔로우 대상 ID", required = true, example = "4")
            @RequestParam Long targetId) {
        return new ResponseEntity<>(followService.makeFollow(sourceId, targetId), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "언팔로우를 하는 API", notes = "소스ID -> 타겟ID로 언팔로우하는 API")
    public ResponseEntity<FollowResultDto> unfollow(
            @ApiParam(value = "팔로우하는 소스 ID(나 자신)", required = true, example = "3")
            @RequestParam Long sourceId,
            @ApiParam(value = "팔로우 대상 ID", required = true, example = "4")
            @RequestParam Long targetId) {
        return new ResponseEntity<>(followService.unfollow(sourceId, targetId), HttpStatus.OK);
    }

    @GetMapping("/followerList")
    @ApiOperation(value = "팔로우(나를 팔로우하는 사람들) 리스트 조회하기", notes = "나를 팔로우하는 사람들을 조회하는 API")
    public ResponseEntity<FollowerListDto> getFollowerList(
            @ApiParam(value = "유저 ID(나 자신)", required = true, example = "3")
            @RequestParam Long accountId) {
        return new ResponseEntity<>(followService.getFollowerList(accountId), HttpStatus.OK);
    }

    @GetMapping("/followingList")
    @ApiOperation(value = "팔로잉(내가 팔로우하는 사람들) 리스트 조회하기", notes = "내가 팔로우하는 사람들을 조회하는 API")
    public ResponseEntity<FollowingListDto> getFollowingList(
            @ApiParam(value = "유저 ID(나 자신)", required = true, example = "3")
            @RequestParam Long accountId) {
        return new ResponseEntity<>(followService.getFollowingList(accountId), HttpStatus.OK);
    }
}
