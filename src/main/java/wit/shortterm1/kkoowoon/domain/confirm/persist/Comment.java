package wit.shortterm1.kkoowoon.domain.confirm.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity @Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "isSelfComment")
    private boolean isSelfComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirm_id")
    private Confirm confirm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Comment(String description, boolean isSelfComment, Confirm confirm, Account account) {
        this.description = description;
        this.isSelfComment = isSelfComment;
        this.confirm = confirm;
        this.account = account;
    }

    public static Comment of(String description, boolean isSelfComment, Confirm confirm, Account account) {
        return new Comment(description, isSelfComment, confirm, account);
    }

    public void updateDescription(String newDescription) {
        description = newDescription;
    }
}
