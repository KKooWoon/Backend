package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.race.persist.Participate;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoWithScoreDto {

    @ApiModelProperty(value = "레이스 ID", required = true, example = "3")
    private Long accountId;

    @ApiModelProperty(value = "유저 이름", required = true, example = "꾸운닉123")
    private String nickname;

    @ApiModelProperty(value = "스코어", required = true, example = "3")
    private int score;

    @ApiModelProperty(value = "프로필 사진 Url", required = true, example = "3")
    private String photoUrl;

    @ApiModelProperty(value = "연속 일수", required = true, example = "12")
    private int consecutiveDays;

    private UserInfoWithScoreDto(Long accountId, String nickname, int score, String photoUrl, int consecutiveDays) {
        this.accountId = accountId;
        this.nickname = nickname;
        this.score = score;
        this.photoUrl = photoUrl;
        this.consecutiveDays = consecutiveDays;
    }

    public static UserInfoWithScoreDto createDto(Account account, Participate participate) {
        return new UserInfoWithScoreDto(account.getId(), account.getNickname(), participate.getTotalScore(), account.getPhotoUrl(), participate.getConsecutiveDays());
    }
}
