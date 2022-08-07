package wit.shortterm1.kkoowoon.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoDto {

    private String nickname;

    private int age;

    private double height;

    private double weight;

    private int level;

    private int exp;

    private double bodyFatPct;

    private double skeletalMuscleMass;

    private String description;

    private String profileImageUrl;

    private UserInfoDto(String nickname, int age, double height, double weight, int level,
                        int exp, double bodyFatPct, double skeletalMuscleMass, String description, String profileImageUrl) {
        this.nickname = nickname;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.level = level;
        this.exp = exp;
        this.bodyFatPct = bodyFatPct;
        this.skeletalMuscleMass = skeletalMuscleMass;
        this.description = description;
        this.profileImageUrl = profileImageUrl;
    }

    public static UserInfoDto createDto(Account account) {
        return new UserInfoDto(account.getNickname(), account.getAge(), account.getHeight(), account.getWeight(), account.getLevel(),
                account.getExp(), account.getBodyFatPct(), account.getSkeletalMuscleMass(), account.getDescription(), account.getProfileImageUrl());

    }
}
