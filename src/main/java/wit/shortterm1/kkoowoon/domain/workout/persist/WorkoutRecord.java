package wit.shortterm1.kkoowoon.domain.workout.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.confirm.exception.AlreadyConfirmedException;
import wit.shortterm1.kkoowoon.domain.confirm.exception.YetConfirmedException;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    Race race;

    @OneToMany(mappedBy = "workoutRecord")
    private List<Cardio> cardioList = new ArrayList<>();

    @OneToMany(mappedBy = "workoutRecord")
    private List<Weight> weightList = new ArrayList<>();

    @OneToMany(mappedBy = "workoutRecord")
    private List<Diet> dietList = new ArrayList<>();


    private WorkoutRecord(boolean isConfirmed, double currentWeight, LocalDate recordDate, Account account, Race race) {
        this.isConfirmed = isConfirmed;
        this.currentWeight = currentWeight;
        this.recordDate = recordDate;
        this.account = account;
        this.race = race;
    }

    public static WorkoutRecord of(boolean isConfirmed, double currentWeight, LocalDate recordDate, Account account, Race race) {
        return new WorkoutRecord(isConfirmed, currentWeight, recordDate, account, race);
    }

    public void confirmWorkout() {
        if (isConfirmed) {
            throw new AlreadyConfirmedException(ErrorCode.ALREADY_CONFIRMED);
        }
        isConfirmed = true;
    }

    public void revertConfirmation() {
        if (!isConfirmed) {
            throw new YetConfirmedException(ErrorCode.YET_CONFIRMED);
        }
        isConfirmed = false;
    }
}
