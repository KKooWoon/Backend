package wit.shortterm1.kkoowoon.domain.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

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

    private Account(String kakaoId, String nickname, int age,
                    double height, double weight, int level, int exp,
                    double bodyFatPct, double skeletalMuscleMass, String description) {
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
        role = "USER";
    }

    public static Account of(String kakaoId, String nickname, int age,
                             double height, double weight, int level, int exp,
                             double bodyFatPct, double skeletalMuscleMass, String description) {
        return new Account(kakaoId, nickname, age, height, weight, level, exp, bodyFatPct, skeletalMuscleMass, description);
    }
}
