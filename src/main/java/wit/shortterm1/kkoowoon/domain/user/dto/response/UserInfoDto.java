package wit.shortterm1.kkoowoon.domain.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoDto {

    @ApiModelProperty(value = "회원 ID", required = true, example = "3")
    private Long accountId;

    @ApiModelProperty(value = "닉네임", required = true, example = "꾸운닉123")
    private String nickname;

    @ApiModelProperty(value = "나이", required = true, example = "20")
    private int age;

    @ApiModelProperty(value = "키", required = true, example = "192.5")
    private double height;

    @ApiModelProperty(value = "몸묵게", required = true, example = "85.6")
    private double weight;

    @ApiModelProperty(value = "레벨", required = true, example = "2")
    private int level;

    @ApiModelProperty(value = "경험치", required = true, example = "4500")
    private int exp;

    @ApiModelProperty(value = "체지방량", required = true, example = "30.4")
    private double bodyFat;

    @ApiModelProperty(value = "골격른량", required = true, example = "35.1")
    private double skeletalMuscleMass;

    @ApiModelProperty(value = "상태 메시지", required = true, example = "운동 쉽니다 다쳤어요ㅠㅠ")
    private String description;

    @ApiModelProperty(value = "키워드", required = true, example = "#다이어트")
    private String keyword;

    @ApiModelProperty(value = "프로필 사진 주소", required = true, example = "abc.png")
    private String photoUrl;

    private UserInfoDto(Long accountId, String nickname, int age, double height,
                        double weight, int level, int exp, double bodyFat,
                        double skeletalMuscleMass, String description, String keyword, String photoUrl) {
        this.accountId = accountId;
        this.nickname = nickname;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.level = level;
        this.exp = exp;
        this.bodyFat = bodyFat;
        this.skeletalMuscleMass = skeletalMuscleMass;
        this.description = description;
        this.keyword = keyword;
        this.photoUrl = photoUrl;
    }

    public static UserInfoDto createDto(Account account) {
        return new UserInfoDto(account.getId(), account.getNickname(), account.getAge(), account.getHeight(), account.getWeight(), account.getLevel(),
                account.getExp(), account.getBodyFatPct(), account.getSkeletalMuscleMass(), account.getDescription(), account.getKeyword(), account.getPhotoUrl());

    }
}
