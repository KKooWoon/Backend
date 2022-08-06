package wit.shortterm1.kkoowoon.domain.etc.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowInfoDto {

    @ApiModelProperty(value = "팔로우하는 소스 id(나 자신)", required = true, example = "1")
    private String sourceNickname;

    @ApiModelProperty(value = "팔로우 대상 id", required = true, example = "3")
    private String targetNickname;

    @ApiModelProperty(value = "프로필 이미지 URL", required = true, example = "image.url")
    private String profileImageUrl;

    @ApiModelProperty(value = "상태 메시지", required = true, example = "운동 화이팅 아자아자!")
    private String description;

    private FollowInfoDto(String sourceNickname, String targetNickname, String profileImageUrl, String description) {
        this.sourceNickname = sourceNickname;
        this.targetNickname = targetNickname;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }

    public static FollowInfoDto createDto(String sourceNickname, Account targetAccount) {
        return new FollowInfoDto(sourceNickname, targetAccount.getNickname(), targetAccount.getProfileImageUrl(), targetAccount.getDescription());
    }
}
