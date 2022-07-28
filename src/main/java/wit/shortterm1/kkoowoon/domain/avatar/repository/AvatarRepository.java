package wit.shortterm1.kkoowoon.domain.avatar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wit.shortterm1.kkoowoon.domain.avatar.persist.Avatar;

import java.util.List;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
