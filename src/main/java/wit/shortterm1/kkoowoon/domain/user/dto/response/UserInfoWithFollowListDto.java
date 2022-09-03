package wit.shortterm1.kkoowoon.domain.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoWithFollowListDto {

    @ApiModelProperty(value = "회원 ID", required = true, example = "3")
    private final List<UserInfoWithFollowDto> userInfoWithFollowList = new ArrayList<>();

    public static UserInfoWithFollowListDto createDto() {
        return new UserInfoWithFollowListDto();
    }

    public void addUserInfoWithFollow(UserInfoWithFollowDto dto) {
        userInfoWithFollowList.add(dto);
    }

}
