package wit.shortterm1.kkoowoon.domain.avatar.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wit.shortterm1.kkoowoon.domain.avatar.dto.response.UnlockAvatarDto;
import wit.shortterm1.kkoowoon.domain.avatar.dto.response.UnlockAvatarListDto;
import wit.shortterm1.kkoowoon.domain.avatar.service.AvatarService;

@RestController
@Api(tags = "아바타 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    @GetMapping("/earned")
    @ApiOperation(value = "사용자가 획득한 아바타 리스트를 리턴", notes = "사용자 id값을 통해 획득한 아바타 리스트를 가져오는 API")
    public ResponseEntity<UnlockAvatarListDto> findEarnedAvatarList(
            @ApiParam(value = "사용자 id", required = true, example = "1")
            @RequestParam Long accountId) {
        return new ResponseEntity<>(avatarService.getEarnedAvatarList(accountId), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "사용자가 가장 최근 획득한 아바타 1개를 리턴", notes = "사용자 id값을 통해 가장 최근 획득한 아바타를 가져오는 API")
    public ResponseEntity<UnlockAvatarDto> findEarnedAvatar(
            @ApiParam(value = "사용자 id", required = true, example = "1")
            @RequestParam Long accountId,
            @ApiParam(value = "사용자 현재 레벨", required = true, example = "3")
            @RequestParam int level) {
        return new ResponseEntity<>(avatarService.getLatestEarnedAvatar(accountId, level), HttpStatus.OK);
    }


}
