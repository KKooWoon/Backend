package wit.shortterm1.kkoowoon.domain.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "액세스 토큰", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiItMTk0OTc2Njg4OSIsInJvbGVzIjoiVVNFUiIsImlhdCI6MTY1ODA3MDUxOCwiZXhwIjoxNjU4MDcyMzE4fQ.jlM2w8CTv1rDA3z-FkuTQFycw3zspreoNb5JWBS-FNA")
    private String accessToken;

    @ApiModelProperty(value = "리프레시 토큰", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiItMTk0OTc2Njg4OSIsInJvbGVzIjoiVVNFUiIsImlhdCI6MTY1ODA3MDUxOCwiZXhwIjoxNjU4MDcyMzE4fQ.jlM2w8CTv1rDA3z-FkuTQFycw3zspreoNb5JWBS-FNA")
    private String refreshToken;

    @ApiModelProperty(value = "유저 정보 DTO", required = true, example = "userInfoDto")
    private UserInfoDto userInfoDto;

    public LoginResponseDto(boolean isSuccess, String accessToken, String refreshToken, UserInfoDto userInfoDto) {
        this.isSuccess = isSuccess;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfoDto = userInfoDto;
    }

    public static LoginResponseDto createDto(String accessToken, String refreshToken, Account account) {
        return new LoginResponseDto(true, accessToken, refreshToken, UserInfoDto.createDto(account));
    }

}
