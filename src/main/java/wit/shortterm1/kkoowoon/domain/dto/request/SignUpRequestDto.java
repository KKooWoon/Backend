package wit.shortterm1.kkoowoon.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wit.shortterm1.kkoowoon.domain.domain.Account;

@AllArgsConstructor
@Getter
public class SignUpRequestDto {
    private String code;

    private String kakaoId;

    private String nickname;

    private int age;

    private double height;

    private double weight;

    private double bodyFatPct;

    private double skeletalMuscleMass;

    private String description;

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    public Account toEntity() {
        return Account.of(kakaoId, nickname, age, height, weight, 1, 0, bodyFatPct, skeletalMuscleMass, description);
    }
}
