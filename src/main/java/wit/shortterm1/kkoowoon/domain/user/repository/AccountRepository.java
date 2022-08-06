package wit.shortterm1.kkoowoon.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByKakaoId(String kakaoId);
    boolean existsByNickname(String nickname);

    Optional<Account> findByKakaoId(String kakaoId);

    Optional<Account> findByNickname(String nickname);
}
