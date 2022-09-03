package wit.shortterm1.kkoowoon.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Transactional(readOnly = true)
    boolean existsByKakaoId(String kakaoId);

    @Transactional(readOnly = true)
    boolean existsByNickname(String nickname);

    @Transactional(readOnly = true)
    Optional<Account> findByKakaoId(String kakaoId);

    @Transactional(readOnly = true)
    Optional<Account> findByNickname(String nickname);

    @Transactional(readOnly = true)
    @Query("SELECT a" +
            " FROM Account a " +
            " WHERE a.nickname LIKE :pattern")
    List<Account> findAllByName(@Param("pattern") String pattern);
}
