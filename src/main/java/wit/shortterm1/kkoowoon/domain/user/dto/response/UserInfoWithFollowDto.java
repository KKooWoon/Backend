package wit.shortterm1.kkoowoon.domain.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoWithFollowDto {

    @ApiModelProperty(value = "회원 ID", required = true, example = "3")
    private Long accountId;

    @ApiModelProperty(value = "닉네임", required = true, example = "꾸운닉123")
    private String nickname;

    @ApiModelProperty(value = "닉네임", required = true, example = "꾸운닉123")
    private String profilePhotoUrl;

    @ApiModelProperty(value = "팔로우 여부", required = true, example = "true/false")
    private boolean isFollow;

    private UserInfoWithFollowDto(Long accountId, String nickname, String profilePhotoUrl, boolean isFollow) {
        this.accountId = accountId;
        this.nickname = nickname;
        this.profilePhotoUrl = profilePhotoUrl;
        this.isFollow = isFollow;
    }

    public static UserInfoWithFollowDto createDto(Long accountId, String nickname, String profilePhotoUrl, boolean isFollow) {
        return new UserInfoWithFollowDto(accountId, nickname,profilePhotoUrl, isFollow);
    }
}
