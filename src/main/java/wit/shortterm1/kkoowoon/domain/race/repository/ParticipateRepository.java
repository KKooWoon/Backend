package wit.shortterm1.kkoowoon.domain.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.race.persist.Participate;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipateRepository extends JpaRepository<Participate, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT CASE WHEN COUNT(p) > 0 " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Participate p " +
            "WHERE p.account.id=:accountId AND p.race.id=:raceId")
    boolean existsByAccountAndRace(@Param("accountId") Long accountId, @Param("raceId")Long raceId);

    @Transactional(readOnly = true)
    @Query("SELECT p " +
            "FROM Participate p " +
            " LEFT JOIN FETCH p.race r" +
            " LEFT JOIN FETCH p.account ac" +
            " WHERE ac.id =:accountId AND r.id =:raceId")
    Optional<Participate> findByAccountAndRace(@Param("accountId") Long accountId, @Param("raceId") Long raceId);

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Participate p" +
            " LEFT JOIN FETCH p.race r" +
            " LEFT JOIN FETCH p.account ac" +
            " WHERE ac.id =:accountId" +
            " ORDER BY p.createdAt DESC")
    List<Participate> findAllByAccountId(@Param("accountId") Long accountId);

    @Transactional(readOnly = true)
    @Query("SELECT p FROM Participate p" +
            " LEFT JOIN FETCH p.race r" +
            " LEFT JOIN FETCH p.account ac" +
            " WHERE r.id =:raceId")
    List<Participate> findAllByRaceId(@Param("raceId") Long raceId);
}
