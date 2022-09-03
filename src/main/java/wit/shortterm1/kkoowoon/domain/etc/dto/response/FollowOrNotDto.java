package wit.shortterm1.kkoowoon.domain.etc.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowOrNotDto {

    @ApiModelProperty(value = "팔로우하는 소스 id(나 자신)", required = true, example = "1")
    private Long sourceId;

    @ApiModelProperty(value = "유저 ID(팔로우 여부를 확인할 대상)", required = true, example = "2")
    private Long targetId;

    @ApiModelProperty(value = "팔로우 여부", required = true, example = "true/false")
    private boolean isFollowing;

    private FollowOrNotDto(Long sourceId, Long targetId, boolean isFollowing) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.isFollowing = isFollowing;
    }

    public static FollowOrNotDto createDto(Long sourceId, Long targetId, boolean isFollowing) {
        return new FollowOrNotDto(sourceId, targetId, isFollowing);
    }
}
