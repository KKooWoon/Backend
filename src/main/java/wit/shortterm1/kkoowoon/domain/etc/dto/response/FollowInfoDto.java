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
    private Long sourceId;

    @ApiModelProperty(value = "팔로우하는 소스 닉네임(나 자신)", required = true, example = "꾸운닉123")
    private String sourceNickname;

    @ApiModelProperty(value = "팔로우 대상 닉네임", required = true, example = "3")
    private Long targetId;

    @ApiModelProperty(value = "팔로우 대상 닉네임", required = true, example = "닉네임999")
    private String targetNickname;

    @ApiModelProperty(value = "프로필 이미지 URL", required = true, example = "image.url")
    private String profileImageUrl;

    @ApiModelProperty(value = "상태 메시지", required = true, example = "운동 화이팅 아자아자!")
    private String description;

    private FollowInfoDto(Long sourceId, String sourceNickname, Long targetId,
                          String targetNickname, String profileImageUrl, String description) {
        this.sourceId = sourceId;
        this.sourceNickname = sourceNickname;
        this.targetId = targetId;
        this.targetNickname = targetNickname;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }

    public static FollowInfoDto createDto(Account source, Account target) {
        return new FollowInfoDto(source.getId(), source.getNickname(), target.getId(),
                target.getNickname(), target.getProfileImageUrl(), target.getDescription());
    }
}
