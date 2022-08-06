package wit.shortterm1.kkoowoon.domain.race.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wit.shortterm1.kkoowoon.domain.race.persist.RaceInvitationCode;

import java.util.List;

@Repository
public interface RaceInvitationRepository extends JpaRepository<RaceInvitationCode, Long> {

    @Query("SELECT i.invitationCode FROM RaceInvitationCode i")
    List<String> findAllCode();
}
