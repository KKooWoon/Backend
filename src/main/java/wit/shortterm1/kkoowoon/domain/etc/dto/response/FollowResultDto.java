package wit.shortterm1.kkoowoon.domain.etc.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowResultDto {

    @ApiModelProperty(value = "팔로우하는 소스 id(나 자신)", required = true, example = "1")
    private String sourceNickname;

    @ApiModelProperty(value = "팔로우 대상 id", required = true, example = "3")
    private String targetNickname;

    @ApiModelProperty(value = "팔로우 여부", required = true, example = "true/false")
    private Boolean isFollow;

    @ApiModelProperty(value = "API 동작 성공 여부", required = true, example = "true/false")
    private Boolean isSuccess;

    private FollowResultDto(String sourceNickname, String targetNickname, Boolean isFollow, Boolean isSuccess) {
        this.sourceNickname = sourceNickname;
        this.targetNickname = targetNickname;
        this.isFollow = isFollow;
        this.isSuccess = isSuccess;
    }

    public static FollowResultDto createDto(String sourceNickname, String targetNickname, Boolean isFollow, Boolean isSuccess) {
        return new FollowResultDto(sourceNickname, targetNickname, isFollow, isSuccess);
    }
}
