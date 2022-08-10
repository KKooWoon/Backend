package wit.shortterm1.kkoowoon.domain.race.persist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.race.dto.request.RaceUpdateDto;
import wit.shortterm1.kkoowoon.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@Table(name = "race")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Race extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "race_id")
    private Long id;

    @Column(name = "started_at")
    private LocalDate startedAt;

    @Column(name = "ended_at")
    private LocalDate endedAt;

    @Column(name = "member_count")
    private int memberCount;

    @Column(name = "name")
    private String name;

    @Column(name = "race_code")
    private String raceCode;

    @Column(name = "race_pw")
    private String racePassword;

    @Column(name = "race_owner")
    private String raceOwner;

    @Column(name = "race_tag")
    private String raceTag;

    private Race(LocalDate startedAt, LocalDate endedAt, String name,
                 String raceCode, String racePassword, String raceOwner, String raceTag) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        memberCount = 1;
        this.name = name;
        this.raceCode = raceCode;
        this.racePassword = racePassword;
        this.raceOwner = raceOwner;
        this.raceTag = raceTag;
    }

    public static Race of(LocalDate startedAt, LocalDate endedAt, String name,
                          String raceCode, String racePassword, String raceOwner, String raceTag) {
        return new Race(startedAt, endedAt, name, raceCode, racePassword, raceOwner, raceTag);
    }

    public void addMemberCount() {
        memberCount += 1;
    }

    public void subtractMemberCount() {
        memberCount -= 1;
    }

    public void updateRace(RaceUpdateDto updateDto) {
        startedAt = updateDto.getStartedAt();
        endedAt = updateDto.getEndedAt();
        name = updateDto.getRaceName();
        racePassword = updateDto.getRacePassword();
        raceOwner = updateDto.getOwnerNickname();
        raceTag = updateDto.getRaceTag();
    }
}
