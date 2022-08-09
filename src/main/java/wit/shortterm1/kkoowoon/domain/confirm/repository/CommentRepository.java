package wit.shortterm1.kkoowoon.domain.confirm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c" +
            " LEFT JOIN FETCH c.confirm cf" +
            " LEFT JOIN FETCH c.account ac" +
            " WHERE cf.id =:confirmId")
    List<Comment> findListById(@Param("confirmId") Long confirmId);
}
