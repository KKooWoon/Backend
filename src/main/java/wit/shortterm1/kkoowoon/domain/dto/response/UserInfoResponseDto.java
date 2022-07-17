package wit.shortterm1.kkoowoon.domain.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.domain.Account;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDto {

    private String nickname;

    private int age;

    private double height;

    private double weight;

    private int level;

    private int exp;

    private double bodyFatPct;

    private double skeletalMuscleMass;

    private String description;

    private UserInfoResponseDto(String nickname, int age, double height, double weight, int level,
                               int exp, double bodyFatPct, double skeletalMuscleMass, String description) {
        this.nickname = nickname;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.level = level;
        this.exp = exp;
        this.bodyFatPct = bodyFatPct;
        this.skeletalMuscleMass = skeletalMuscleMass;
        this.description = description;
    }

    public static UserInfoResponseDto createDto(Account account) {
        return new UserInfoResponseDto(account.getNickname(), account.getAge(), account.getHeight(), account.getWeight(), account.getLevel(),
                account.getExp(), account.getBodyFatPct(), account.getSkeletalMuscleMass(), account.getDescription());

    }
}
