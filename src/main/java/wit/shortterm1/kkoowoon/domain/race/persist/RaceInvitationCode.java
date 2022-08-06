package wit.shortterm1.kkoowoon.domain.race.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity @Getter
@Table(name = "race_invitation_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RaceInvitationCode extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_id")
    private Long id;

    @Column(name = "invitation_code")
    private String invitationCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id")
    private Race race;

    private RaceInvitationCode(String invitationCode, Race race) {
        this.invitationCode = invitationCode;
        this.race = race;
    }

    public static RaceInvitationCode of(String invitationCode, Race race) {
        return new RaceInvitationCode(invitationCode, race);
    }
}
