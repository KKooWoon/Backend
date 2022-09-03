package wit.shortterm1.kkoowoon.domain.user.exception;

import wit.shortterm1.kkoowoon.global.error.exception.EntityNotFoundException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class NoSuchKakaoUserException extends EntityNotFoundException {

    private String kakaoId;
    private String profilePhotoUrl;

    public NoSuchKakaoUserException(String kakaoId, String profilePhotoUrl, ErrorCode errorCode) {
        super(errorCode);
        this.kakaoId = kakaoId;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }
}
