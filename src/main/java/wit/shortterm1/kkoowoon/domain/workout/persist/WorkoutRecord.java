package wit.shortterm1.kkoowoon.domain.workout.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "workout_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkoutRecord extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_record_id")
    private Long id;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @Column(name = "current_weight")
    private double currentWeight;

    @Column(name = "record_date")
    private LocalDate recordDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    Account account;
//
//    @OneToMany(mappedBy = "diet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Diet> dietList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "weight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Weight> weightList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "cardio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Cardio> cardioList = new ArrayList<>();

    private WorkoutRecord(boolean isConfirmed, double currentWeight, LocalDate recordDate, Account account) {
        this.isConfirmed = isConfirmed;
        this.currentWeight = currentWeight;
        this.recordDate = recordDate;
        this.account = account;
    }

    public static WorkoutRecord of(boolean isConfirmed, double currentWeight, LocalDate recordDate, Account account) {
        return new WorkoutRecord(isConfirmed, currentWeight, recordDate, account);
    }
}
