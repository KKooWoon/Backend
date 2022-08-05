package wit.shortterm1.kkoowoon.domain.etc.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity @Getter
@Table(name = "follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Follow extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "account_id", name = "sourceId")
    private Account source; // 팔로우를 하는 당사자(나 자신)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "account_id", name = "followingId")
    private Account following; // 팔로우 대상

    private Follow(Account source, Account following) {
        this.source = source;
        this.following = following;
    }

    public static Follow of(Account source, Account following) {
        return new Follow(source, following);
    }
}
