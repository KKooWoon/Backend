package wit.shortterm1.kkoowoon.domain.workout.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity @Getter
@Table(name = "weight")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Weight extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weight_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_record_id")
    private WorkoutRecord workoutRecord;

    private Weight(String name, String body, WorkoutRecord workoutRecord) {
        this.name = name;
        this.body = body;
        this.workoutRecord = workoutRecord;
    }

    public static Weight of(String name, String body, WorkoutRecord workoutRecord) {
        return new Weight(name, body, workoutRecord);
    }

}
