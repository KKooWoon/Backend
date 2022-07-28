package wit.shortterm1.kkoowoon.domain.avatar.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.avatar.persist.Avatar;
import wit.shortterm1.kkoowoon.domain.avatar.persist.UnlockAvatar;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnlockAvatarDto {

    @ApiModelProperty(value = "잠금 해제된 아바타 레벨", required = true, example = "3")
    private int avatarLevel;

    @ApiModelProperty(value = "잠금 해제된 아바타 이름", required = true, example = "레벨3 아바타")
    private String avatarName;

    @ApiModelProperty(value = "잠금 해제된 아바타 id", required = true, example = "https://kkoowoon.com/avatar/3")
    private String avatarUrl;

    @ApiModelProperty(value = "잠금 해제 시점", required = true, example = "2022-01-07")
    private LocalDate earnedAt;

    private UnlockAvatarDto(int avatarLevel, String avatarName, String avatarUrl, LocalDate earnedAt) {
        this.avatarLevel = avatarLevel;
        this.avatarName = avatarName;
        this.avatarUrl = avatarUrl;
        this.earnedAt = earnedAt;
    }

    public static UnlockAvatarDto createDto(Avatar avatar, UnlockAvatar unlockAvatar) {
        return new UnlockAvatarDto(avatar.getLevel(), avatar.getName(), avatar.getAvatarUrl(), unlockAvatar.getCreatedAt().toLocalDate());
    }

}
