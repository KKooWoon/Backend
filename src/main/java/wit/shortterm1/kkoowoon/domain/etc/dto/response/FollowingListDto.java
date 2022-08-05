package wit.shortterm1.kkoowoon.domain.etc.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowingListDto {

    @ApiModelProperty(
            value = "내가 팔로우하는 사람들 정보 리스트",
            required = true,
            example = "[ { 팔로잉1 정보 }, { 팔로잉2 정보 }, ... ]")
    private final List<FollowInfoDto> followerList = new ArrayList<>();

    public void addFollowing(FollowInfoDto followInfoDto) {
        followerList.add(followInfoDto);
    }

    public static FollowingListDto createDto() {
        return new FollowingListDto();
    }
}
