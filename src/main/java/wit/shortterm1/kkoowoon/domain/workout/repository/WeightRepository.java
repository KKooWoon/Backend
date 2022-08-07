package wit.shortterm1.kkoowoon.domain.workout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.workout.persist.Diet;
import wit.shortterm1.kkoowoon.domain.workout.persist.Weight;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {

    @Query("SELECT w FROM Weight w" +
            " LEFT JOIN FETCH w.workoutRecord wr" +
            " WHERE wr =:record")
    @Transactional(readOnly = true)
    List<Weight> findByRecord(@Param("record") WorkoutRecord record);

    @Query("SELECT w FROM Weight w" +
            " LEFT JOIN FETCH w.workoutRecord wr" +
            " WHERE w.id =:weightId")
    @Transactional(readOnly = true)
    Optional<Weight> findByIdWithRecord(@Param("weightId") Long weightId);
}