package wit.shortterm1.kkoowoon.domain.etc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.etc.persist.Follow;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT f FROM Follow f" +
            " LEFT JOIN FETCH f.source s" +
            " LEFT JOIN FETCH f.following fl" +
            " WHERE s.id =:sourceId AND fl.id =:targetId")
    @Transactional(readOnly = true)
    Optional<Follow> findFollowByTwo(@Param("sourceId") Long sourceId, @Param("targetId") Long targetId);

    @Query("SELECT f FROM Follow f" +
            " LEFT JOIN FETCH f.source s" +
            " WHERE f.following =:target")
    @Transactional(readOnly = true)
    List<Follow> findFollowerListByTarget(@Param("target") Account target);

    @Query("SELECT f FROM Follow f" +
            " LEFT JOIN FETCH f.following fl" +
            " WHERE f.source =:source")
    @Transactional(readOnly = true)
    List<Follow> findFollowingListBySource(@Param("source") Account source);


}
