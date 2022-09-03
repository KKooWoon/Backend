package wit.shortterm1.kkoowoon.domain.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;
import wit.shortterm1.kkoowoon.domain.race.persist.Participate;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RaceRepository extends JpaRepository<Race, Long> {

    @Transactional(readOnly = true)
    Optional<Race> findByRaceCode(String raceCode);

    @Transactional(readOnly = true)
    @Query("SELECT r " +
            "FROM Race r " +
            " WHERE r.name LIKE :pattern")
    List<Race> findAllByName(@Param("pattern") String pattern);

    @Transactional(readOnly = true)
    @Query("SELECT r " +
            "FROM Race r " +
            " WHERE r.raceTag =:tag")
    List<Race> findAllByTag(@Param("tag") String tag);
}
