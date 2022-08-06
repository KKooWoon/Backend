package wit.shortterm1.kkoowoon.domain.workout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.workout.persist.Cardio;
import wit.shortterm1.kkoowoon.domain.workout.persist.Weight;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardioRepository extends JpaRepository<Cardio, Long> {

    @Query("SELECT c FROM Cardio c" +
            " LEFT JOIN FETCH c.workoutRecord wr" +
            " WHERE c.id =:cardioId")
    @Transactional(readOnly = true)
    Optional<Cardio> findByIdWithRecord(@Param("cardioId") Long cardioId);

    @Query("SELECT c FROM Cardio c" +
            " LEFT JOIN FETCH c.workoutRecord wr" +
            " WHERE wr.id =:recordId")
    @Transactional(readOnly = true)
    List<Cardio> findByRecordId(@Param("recordId") Long recordId);
}
