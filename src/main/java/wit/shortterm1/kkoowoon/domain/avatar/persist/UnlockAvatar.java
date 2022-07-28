package wit.shortterm1.kkoowoon.domain.avatar.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "unlock_avatar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnlockAvatar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unlock_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    private UnlockAvatar(Account account, Avatar avatar) {
        this.account = account;
        this.avatar = avatar;
    }

    public static UnlockAvatar of(Account account, Avatar avatar) {
        return new UnlockAvatar(account, avatar);
    }
}
