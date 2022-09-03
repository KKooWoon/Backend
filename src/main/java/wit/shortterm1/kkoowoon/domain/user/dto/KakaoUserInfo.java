package wit.shortterm1.kkoowoon.domain.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUserInfo {

    private String kakaoId;

    private String photoUrl;

    public KakaoUserInfo(String kakaoId, String photoUrl) {
        this.kakaoId = kakaoId;
        this.photoUrl = photoUrl;
    }
}
