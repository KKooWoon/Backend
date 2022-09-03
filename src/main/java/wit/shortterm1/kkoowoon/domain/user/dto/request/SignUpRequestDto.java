package wit.shortterm1.kkoowoon.domain.user.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {

    @ApiModelProperty(value = "카카오 ID", required = true, example = "12131")
    private String kakaoId;

    @ApiModelProperty(value = "닉네임(고유값)", required = true, example = "꾸운닉123")
    private String nickname;

    @ApiModelProperty(value = "나이", required = true, example = "20")
    private int age;

    @ApiModelProperty(value = "키", required = true, example = "165.4")
    private double height;

    @ApiModelProperty(value = "몸무게", required = true, example = "65.5")
    private double weight;

    @ApiModelProperty(value = "체지방량", required = true, example = "30.4")
    private double bodyFat;

    @ApiModelProperty(value = "골격근량", required = true, example = "23.4")
    private double skeletalMuscleMass;

    @ApiModelProperty(value = "상태 메시지", required = true, example = "운동 화이팅 아자아자!")
    private String description;

    @ApiModelProperty(value = "관심 키워드", required = true, example = "#다이어트")
    private String keyword;

    @ApiModelProperty(value = "프로필 사진 URL", required = true, example = "kakao/oauth 시 404 리턴 때 받은 profilePhotoUrl 주면 됨")
    private String profilePhotoUrl;

    public Account toEntity() {
        return Account.of(kakaoId, nickname, age, height, weight, 1, 0, bodyFat, skeletalMuscleMass, description, keyword, profilePhotoUrl);
    }

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }
}
