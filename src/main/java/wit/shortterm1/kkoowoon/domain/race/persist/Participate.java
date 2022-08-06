package wit.shortterm1.kkoowoon.domain.race.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "participate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Participate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participate_id")
    private Long id;

    @Column(name = "consecutive_days")
    private int consecutiveDays;

    @Column(name = "total_score")
    private int totalScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    private Participate(int consecutiveDays, int totalScore, Account account, Race race) {
        this.consecutiveDays = consecutiveDays;
        this.totalScore = totalScore;
        this.account = account;
        this.race = race;
    }

    public static Participate of(Account account, Race race) {
        return new Participate(0, 0, account, race);
    }
}
