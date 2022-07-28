package wit.shortterm1.kkoowoon.domain.avatar.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnlockAvatarListDto {

    @ApiModelProperty(
            value = "잠금 해제된 아바타 리스트",
            required = true,
            example = "[ { 아바타1 정보 }, { 아바타2 정보 }, ... ]")
    private final List<UnlockAvatarDto> unlockAvatarList = new ArrayList<>();

    public void addUnlockAvatar(UnlockAvatarDto unlockAvatarDto) {
        unlockAvatarList.add(unlockAvatarDto);
    }

    public static UnlockAvatarListDto createDto() {
        return new UnlockAvatarListDto();
    }
}
