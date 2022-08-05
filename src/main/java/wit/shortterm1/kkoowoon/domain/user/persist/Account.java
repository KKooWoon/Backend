package wit.shortterm1.kkoowoon.domain.user.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity @Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_kakao_id", nullable = false, unique = true)
    private String kakaoId;

    @Column(name = "account_nickname", nullable = false)
    private String nickname;

    @Column(name = "account_role")
    private String role;

    @Column(name = "account_age")
    private int age;

    @Column(name = "account_height")
    private double height;

    @Column(name = "account_weight")
    private double weight;

    @Column(name = "account_level")
    private int level;

    @Column(name = "account_exp")
    private int exp;

    @Column(name = "account_boyfat_pct")
    private double bodyFatPct;

    @Column(name = "account_skeletal_muscle_mass")
    private double skeletalMuscleMass;

    @Column(name = "account_description")
    private String description;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    private Account(String kakaoId, String nickname, int age,
                    double height, double weight, int level, int exp,
                    double bodyFatPct, double skeletalMuscleMass, String description, String profileImageUrl) {
        this.kakaoId = kakaoId;
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
        role = "USER";
    }

    public static Account of(String kakaoId, String nickname, int age,
                             double height, double weight, int level, int exp,
                             double bodyFatPct, double skeletalMuscleMass, String description, String profileImageUrl) {
        return new Account(kakaoId, nickname, age, height, weight, level, exp, bodyFatPct, skeletalMuscleMass, description, profileImageUrl);
    }
}
