package wit.shortterm1.kkoowoon.domain.avatar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.avatar.persist.UnlockAvatar;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnlockRepository extends JpaRepository<UnlockAvatar, Long> {

    @Query("SELECT u FROM UnlockAvatar u" +
            " LEFT JOIN FETCH u.avatar av" +
            " LEFT JOIN FETCH u.account ac" +
            " WHERE ac.id =:accountId")
    @Transactional(readOnly = true)
    List<UnlockAvatar> findListByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT u FROM UnlockAvatar u" +
            " LEFT JOIN FETCH u.avatar av" +
            " LEFT JOIN FETCH u.account ac" +
            " WHERE ac.id =:accountId AND u.avatar.level =:level")
    @Transactional(readOnly = true)
    Optional<UnlockAvatar> findByAccountId(@Param("accountId")Long accountId, @Param("level") int level);
}
