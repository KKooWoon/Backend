package wit.shortterm1.kkoowoon.domain.workout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long> {

    @Query("SELECT wr FROM WorkoutRecord wr" +
            " LEFT JOIN FETCH wr.account ac" +
            " WHERE ac.id =:accountId" +
            " ORDER BY wr.recordDate DESC")
    @Transactional(readOnly = true)
    List<WorkoutRecord> findAllByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT wr FROM WorkoutRecord wr" +
            " LEFT JOIN FETCH wr.account ac" +
            " LEFT JOIN FETCH wr.race r" +
            " LEFT JOIN FETCH wr.cardioList" +
            " WHERE ac.id =:accountId AND r.id =:raceId AND wr.recordDate =:date")
    @Transactional(readOnly = true)
    Optional<WorkoutRecord> findByAccountNRaceNDate(@Param("accountId") Long accountId, @Param("raceId") Long raceId, @Param("date")LocalDate date);

    @Query("SELECT wr FROM WorkoutRecord wr" +
            " LEFT JOIN FETCH wr.account ac" +
            " LEFT JOIN FETCH wr.race r" +
            " LEFT JOIN FETCH wr.cardioList" +
            " WHERE ac.id =:accountId" +
            " AND r.id =:raceId" +
            " AND wr.recordDate >=:firstDayOfMonth AND wr.recordDate <=:lastDayOfMonth" +
            " ORDER BY wr.recordDate")
    @Transactional(readOnly = true)
    List<WorkoutRecord> findByAccountNRaceWithRange(@Param("accountId") Long accountId, @Param("raceId") Long raceId,
                                                    @Param("firstDayOfMonth")LocalDate firstDayOfMonth, @Param("lastDayOfMonth")LocalDate lastDayOfMonth);


    @Query("SELECT wr FROM WorkoutRecord wr" +
            " LEFT JOIN FETCH wr.account ac" +
            " LEFT JOIN FETCH wr.race r" +
            " LEFT JOIN FETCH wr.cardioList" +
            " WHERE r.id =:raceId" +
            " ORDER BY wr.recordDate DESC")
    @Transactional(readOnly = true)
    List<WorkoutRecord> findAllByRaceId(@Param("raceId") Long raceId);
}