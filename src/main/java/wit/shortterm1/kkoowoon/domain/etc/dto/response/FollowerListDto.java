package wit.shortterm1.kkoowoon.domain.etc.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowerListDto {

    @ApiModelProperty(
            value = "나를 팔로우하는 사람들 정보 리스트",
            required = true,
            example = "[ { 팔로워1 정보 }, { 팔로워2 정보 }, ... ]")
    private final List<FollowInfoDto> followerList = new ArrayList<>();

    public void addFollower(FollowInfoDto followInfoDto) {
        followerList.add(followInfoDto);
    }

    public static FollowerListDto createDto() {
        return new FollowerListDto();
    }
}
