package wit.shortterm1.kkoowoon.domain.confirm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmRepository extends JpaRepository<Confirm, Long> {

    @Query("SELECT cw FROM Confirm cw" +
        " LEFT JOIN FETCH cw.race r" +
        " WHERE r =:race AND cw.confirmedAt =:recordDate")
    List<Confirm> findAllByRaceAndDate(@Param("race") Race race, @Param("recordDate") LocalDate recordDate);

    @Query("SELECT cw FROM Confirm cw" +
            " LEFT JOIN FETCH cw.race r" +
            " LEFT JOIN FETCH cw.workoutRecord wr" +
            " WHERE r =:race AND wr =:workoutRecord")
    Optional<Confirm> findByRaceAndRecord(@Param("race") Race race, @Param("workoutRecord") WorkoutRecord workoutRecord);

//    @Query("SELECT wr FROM WorkoutRecord wr" +
//            " LEFT JOIN FETCH wr.account ac" +
//            " WHERE ac =:account")
//    @Transactional(readOnly = true)
//    Optional<WorkoutRecord> findByAccount(@Param("account") Account account);
//
//    @Query("SELECT wr FROM WorkoutRecord wr" +
//            " LEFT JOIN FETCH wr.account ac" +
//            " WHERE ac =:account AND wr.recordDate =:date")
//    @Transactional(readOnly = true)
//    Optional<WorkoutRecord> findByAccountNDate(@Param("account") Account account, @Param("date")LocalDate date);
}