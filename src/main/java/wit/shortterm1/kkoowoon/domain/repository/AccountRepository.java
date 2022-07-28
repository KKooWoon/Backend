package wit.shortterm1.kkoowoon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wit.shortterm1.kkoowoon.domain.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByKakaoId(String kakaoId);
    boolean existsByNickname(String nickname);

    Optional<Account> findByKakaoId(String kakaoId);

    Optional<Account> findByNickname(String nickname);
}
