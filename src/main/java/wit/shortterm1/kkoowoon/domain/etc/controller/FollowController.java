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
@Api("아바타 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    @ApiOperation(value = "팔로우를 새로 하는 API", notes = "소스ID -> 타겟ID로 팔로우가 되는 API")
    public ResponseEntity<FollowResultDto> follow(
            @ApiParam(value = "팔로우하는 소스 닉네임(나 자신)", required = true, example = "꾸운닉123")
            @RequestParam String sourceNickname,
            @ApiParam(value = "팔로우 대상 닉네임", required = true, example = "인기쟁이333")
            @RequestParam String targetNickname) {
        return new ResponseEntity<>(followService.makeFollow(sourceNickname, targetNickname), HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation(value = "언팔로우를 하는 API", notes = "소스ID -> 타겟ID로 언팔로우하는 API")
    public ResponseEntity<FollowResultDto> unfollow(
            @ApiParam(value = "팔로우하는 소스 닉네임(나 자신)", required = true, example = "꾸운닉123")
            @RequestParam String sourceNickname,
            @ApiParam(value = "팔로우 대상 닉네임", required = true, example = "인기쟁이333")
            @RequestParam String targetNickname) {
        return new ResponseEntity<>(followService.unfollow(sourceNickname, targetNickname), HttpStatus.OK);
    }

    @GetMapping("/followerList")
    @ApiOperation(value = "팔로우(나를 팔로우하는 사람들) 리스트 조회하기", notes = "나를 팔로우하는 사람들을 조회하는 API")
    public ResponseEntity<FollowerListDto> getFollowerList(
            @ApiParam(value = "닉네임(나 자신)", required = true, example = "꾸운닉123")
            @RequestParam String nickname) {
        return new ResponseEntity<>(followService.getFollowerList(nickname), HttpStatus.OK);
    }

    @GetMapping("/followingList")
    @ApiOperation(value = "팔로잉(내가 팔로우하는 사람들) 리스트 조회하기", notes = "내가 팔로우하는 사람들을 조회하는 API")
    public ResponseEntity<FollowingListDto> getFollowingList(
            @ApiParam(value = " 닉네임(나 자신)", required = true, example = "꾸운닉123")
            @RequestParam String nickname) {
        return new ResponseEntity<>(followService.getFollowingList(nickname), HttpStatus.OK);
    }
}
