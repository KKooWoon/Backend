package wit.shortterm1.kkoowoon.domain.workout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long> {

    @Query("SELECT wr FROM WorkoutRecord wr" +
            " LEFT JOIN FETCH wr.account ac" +
            " WHERE ac =:account")
    @Transactional(readOnly = true)
    Optional<WorkoutRecord> findByAccount(@Param("account") Account account);

    @Query("SELECT wr FROM WorkoutRecord wr" +
            " LEFT JOIN FETCH wr.account ac" +
            " WHERE ac =:account AND wr.recordDate =:date")
    @Transactional(readOnly = true)
    Optional<WorkoutRecord> findByAccountNDate(@Param("account") Account account, @Param("date")LocalDate date);
}
