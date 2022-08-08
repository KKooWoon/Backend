package wit.shortterm1.kkoowoon.domain.etc.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowResultDto {
    @ApiModelProperty(value = "팔로우하는 소스 ID(나 자신)", required = true, example = "1")
    private Long sourceId;

    @ApiModelProperty(value = "팔로우하는 사람 닉네임(나 자신)", required = true, example = "꾸운닉123")
    private String sourceNickname;

    @ApiModelProperty(value = "팔로우 대상 ID", required = true, example = "1")
    private Long targetId;

    @ApiModelProperty(value = "팔로우 대상 닉네임", required = true, example = "닉네임999")
    private String targetNickname;

    @ApiModelProperty(value = "팔로우 여부", required = true, example = "true/false")
    private Boolean isFollow;

    @ApiModelProperty(value = "API 동작 성공 여부", required = true, example = "true/false")
    private Boolean isSuccess;

    private FollowResultDto(Long sourceId, String sourceNickname, Long targetId,
                           String targetNickname, Boolean isFollow, Boolean isSuccess) {
        this.sourceId = sourceId;
        this.sourceNickname = sourceNickname;
        this.targetId = targetId;
        this.targetNickname = targetNickname;
        this.isFollow = isFollow;
        this.isSuccess = isSuccess;
    }

    public static FollowResultDto createDto(Account source, Account target, Boolean isFollow, Boolean isSuccess) {
        return new FollowResultDto(source.getId(), source.getNickname(), target.getId(), target.getNickname(), isFollow, isSuccess);
    }
}
