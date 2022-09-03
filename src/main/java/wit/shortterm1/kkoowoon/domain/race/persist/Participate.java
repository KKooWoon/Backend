package wit.shortterm1.kkoowoon.domain.race.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;
import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "latest_confirm_date")
    private LocalDate latestConfirmDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    public Participate(Account account, Race race) {
        consecutiveDays = 0;
        totalScore = 0;
        latestConfirmDate = null;
        this.account = account;
        this.race = race;
    }

    public static Participate of(Account account, Race race) {
        return new Participate(account, race);
    }

    public void updateFromConfirm(LocalDate date) {
        if (latestConfirmDate != null && latestConfirmDate.plusDays(1L).isEqual(date)) {
            consecutiveDays += 1;
            totalScore += consecutiveDays * 100;
        } else {
            consecutiveDays = 1;
            totalScore += 100;
        }
        latestConfirmDate = date;
    }
}
