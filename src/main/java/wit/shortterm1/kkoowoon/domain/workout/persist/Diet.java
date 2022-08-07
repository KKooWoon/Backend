package wit.shortterm1.kkoowoon.domain.workout.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "diet")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Diet extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diet_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_record_id")
    private WorkoutRecord workoutRecord;

    private Diet(String name, WorkoutRecord workoutRecord) {
        this.name = name;
        this.workoutRecord = workoutRecord;
    }

    public static Diet of(String name, WorkoutRecord workoutRecord) {
        return new Diet(name, workoutRecord);
    }
}
