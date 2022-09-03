package wit.shortterm1.kkoowoon.domain.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpResultDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "가입 시점", required = true, example = "true/false")
    private LocalDateTime signedUpAt;

    @ApiModelProperty(value = "액세스 토큰", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiItMTk0OTc2Njg4OSIsInJvbGVzIjoiVVNFUiIsImlhdCI6MTY1ODA3MDUxOCwiZXhwIjoxNjU4MDcyMzE4fQ.jlM2w8CTv1rDA3z-FkuTQFycw3zspreoNb5JWBS-FNA")
    private String accessToken;

    @ApiModelProperty(value = "리프레시 토큰", required = true, example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiItMTk0OTc2Njg4OSIsInJvbGVzIjoiVVNFUiIsImlhdCI6MTY1ODA3MDUxOCwiZXhwIjoxNjU4MDcyMzE4fQ.jlM2w8CTv1rDA3z-FkuTQFycw3zspreoNb5JWBS-FNA")
    private String refreshToken;

    @ApiModelProperty(value = "유저 정보 DTO", required = true, example = "userInfoDto")
    private UserInfoDto userInfoDto;

    private SignUpResultDto(boolean isSuccess, LocalDateTime signedUpAt,
                           String accessToken, String refreshToken, UserInfoDto userInfoDto) {
        this.isSuccess = isSuccess;
        this.signedUpAt = signedUpAt;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userInfoDto = userInfoDto;
    }

    public static SignUpResultDto createDto(boolean isSuccess, LocalDateTime signedUpAt,
                                            String accessToken, String refreshToken, UserInfoDto userInfoDto) {
        return new SignUpResultDto(isSuccess, signedUpAt, accessToken, refreshToken, userInfoDto);
    }

}
