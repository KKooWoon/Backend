package wit.shortterm1.kkoowoon.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {

    private boolean isSuccess;

    private String message;

    private String accessToken;

    private String refreshToken;


    private LoginResponseDto(boolean isSuccess, String message, String accessToken, String refreshToken) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginResponseDto createDto(String accessToken, String refreshToken) {
        return new LoginResponseDto(true, "", accessToken, refreshToken);
    }

    public static LoginResponseDto createDto(String code) {
        return new LoginResponseDto(false, code, null, null);
    }
}
