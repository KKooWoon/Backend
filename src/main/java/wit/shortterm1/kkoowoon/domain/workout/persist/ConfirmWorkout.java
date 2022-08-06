package wit.shortterm1.kkoowoon.domain.workout.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;

import javax.persistence.*;

@Entity @Getter
@Table(name = "confirm_workout")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfirmWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirm_workout_id")
    private Long id;

    @Column(name = "photo_url_1")
    private String photoUrl1;

    @Column(name = "photo_url_2")
    private String photoUrl2;

    @Column(name = "photo_url_3")
    private String photoUrl3;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_record_id")
    private WorkoutRecord workoutRecord;

    private ConfirmWorkout(String photoUrl1, String photoUrl2, String photoUrl3,
                          String description, Race race, WorkoutRecord workoutRecord) {
        this.photoUrl1 = photoUrl1;
        this.photoUrl2 = photoUrl2;
        this.photoUrl3 = photoUrl3;
        this.description = description;
        this.race = race;
        this.workoutRecord = workoutRecord;
    }

    public static ConfirmWorkout of(String photoUrl1, String photoUrl2, String photoUrl3,
                                    String description, Race race, WorkoutRecord workoutRecord) {
        return new ConfirmWorkout(photoUrl1, photoUrl2, photoUrl3, description, race, workoutRecord);
    }
}
