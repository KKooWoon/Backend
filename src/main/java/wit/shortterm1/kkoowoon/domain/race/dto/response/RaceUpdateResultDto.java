package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceUpdateResultDto {

    @ApiModelProperty(value = "레이스 수정 성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "레이스 id", required = true, example = "3")
    private Long raceId;

    @ApiModelProperty(value = "레이스 업데이트 시점", required = true, example = "2022-07-18T23:01:00")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "레이스 시작 일자", required = true, example = "2022-07-18")
    private LocalDate startedAt;

    @ApiModelProperty(value = "레이스 종료 일자", required = true, example = "2022-08-20")
    private LocalDate endedAt;

    @ApiModelProperty(value = "레이스 주인", required = true, example = "꾸운닉123")
    private String owner;

    @ApiModelProperty(value = "레이스 초대코드", required = true, example = "AIKFKD")
    private String raceCode;

    @ApiModelProperty(value = "레이스 비밀번호", required = true, example = "kkoowoon1234")
    private String racePassword;

    @ApiModelProperty(value = "레이스 이름", required = true, example = "다이어트 레이스!")
    private String raceName;

    private RaceUpdateResultDto(boolean isSuccess, Long raceId, LocalDateTime updatedAt, LocalDate startedAt,
                               LocalDate endedAt, String owner, String raceCode, String racePassword, String raceName) {
        this.isSuccess = isSuccess;
        this.raceId = raceId;
        this.updatedAt = updatedAt;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.owner = owner;
        this.raceCode = raceCode;
        this.racePassword = racePassword;
        this.raceName = raceName;
    }

    public static RaceUpdateResultDto createDto(boolean isSuccess, Race race) {
        return new RaceUpdateResultDto(isSuccess, race.getId(), race.getUpdatedAt(), race.getStartedAt(), race.getEndedAt(),
                race.getRaceOwner(), race.getRaceCode(), race.getRacePassword(), race.getName());
    }
}
