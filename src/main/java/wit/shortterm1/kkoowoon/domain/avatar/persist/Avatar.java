package wit.shortterm1.kkoowoon.domain.avatar.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity @Getter
@Table(name = "avatar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Avatar extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id")
    private Long id;

    @Column(name = "level")
    private int level;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar_url")
    private String avatarUrl;

    public Avatar(int level, String name, String avatarUrl) {
        this.level = level;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    public static Avatar of(int level, String name, String avatarUrl) {
        return new Avatar(level, name, avatarUrl);
    }

}
