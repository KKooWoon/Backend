package wit.shortterm1.kkoowoon.domain.user.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.global.error.ErrorResponse;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoLoginErrorResponse extends ErrorResponse {

    private String kakaoId;

    private String profilePhotoUrl;

    private KakaoLoginErrorResponse(String kakaoId, String profilePhotoUrl, ErrorCode code) {
        super(code);
        this.kakaoId = kakaoId;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public static KakaoLoginErrorResponse of(String kakaoId, String profilePhotoUrl, ErrorCode errorCode) {
        return new KakaoLoginErrorResponse(kakaoId, profilePhotoUrl, errorCode);
    }
}
