package wit.shortterm1.kkoowoon.domain.workout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.workout.persist.Diet;
import wit.shortterm1.kkoowoon.domain.workout.persist.Food;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByDiet(Diet diet);

//    @Query("SELECT d FROM Diet d" +
//            " LEFT JOIN FETCH d.workoutRecord wr" +
//            " WHERE d.id =:dietId")
//    @Transactional(readOnly = true)
//    Optional<Diet> findByIdWithRecord(@Param("dietId") Long dietId);
}