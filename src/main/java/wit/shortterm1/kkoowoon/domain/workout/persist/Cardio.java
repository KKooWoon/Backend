package wit.shortterm1.kkoowoon.domain.workout.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.dto.request.UpdateCardioDto;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.Duration;

@Entity
@Getter
@Table(name = "cardio")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cardio extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardio_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private int duration;

    @Column(name = "calorie")
    private double calorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_record_id")
    private WorkoutRecord workoutRecord;

    private Cardio(String name, int duration, double calorie, WorkoutRecord workoutRecord) {
        this.name = name;
        this.duration = duration;
        this.calorie = calorie;
        this.workoutRecord = workoutRecord;
    }

    public static Cardio of(String name, int duration, double calorie, WorkoutRecord workoutRecord) {
        return new Cardio(name, duration, calorie, workoutRecord);
    }

    public void updateCardio(UpdateCardioDto updateCardioDto) {
        name = updateCardioDto.getName();
        duration = updateCardioDto.getTime();
        calorie = updateCardioDto.getCalorie();
    }
}
