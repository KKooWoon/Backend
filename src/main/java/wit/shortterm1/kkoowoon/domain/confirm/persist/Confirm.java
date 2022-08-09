package wit.shortterm1.kkoowoon.domain.confirm.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@Table(name = "confirm")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Confirm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirm_id")
    private Long id;

    @Column(name = "photo_url_1")
    private String photoUrl1;

    @Column(name = "photo_url_2")
    private String photoUrl2;

    @Column(name = "photo_url_3")
    private String photoUrl3;

    @Column(name = "description")
    private String description;

    @Column(name = "confirmed_at")
    private LocalDate confirmedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_record_id")
    private WorkoutRecord workoutRecord;

    private Confirm(String photoUrl1, String photoUrl2, String photoUrl3,
                    String description, Race race, WorkoutRecord workoutRecord) {
        this.photoUrl1 = photoUrl1;
        this.photoUrl2 = photoUrl2;
        this.photoUrl3 = photoUrl3;
        this.description = description;
        this.confirmedAt = workoutRecord.getRecordDate();
        this.race = race;
        this.workoutRecord = workoutRecord;
    }

    public static Confirm of(String photoUrl1, String photoUrl2, String photoUrl3,
                             String description, Race race, WorkoutRecord workoutRecord) {
        return new Confirm(photoUrl1, photoUrl2, photoUrl3, description, race, workoutRecord);
    }
}