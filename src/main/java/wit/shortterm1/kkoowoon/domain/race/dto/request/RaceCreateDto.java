package wit.shortterm1.kkoowoon.domain.race.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceCreateDto {

    @ApiModelProperty(value = "레이스 시작 일자", required = true, example = "2022-07-17")
    private LocalDate startedAt;

    @ApiModelProperty(value = "레이스 종료 일자", required = true, example = "2022-07-25")
    private LocalDate endedAt;

    @ApiModelProperty(value = "레이스 이름", required = true, example = "다이어트 레이스!")
    private String raceName;

    @ApiModelProperty(value = "레이스 비밀번호", required = true, example = "kkoowoon1234")
    private String racePassword;

    @ApiModelProperty(value = "레이스 해시태그", required = true, example = "#다이어트")
    private String raceTag;

    private RaceCreateDto(LocalDate startedAt, LocalDate endedAt,
                          String raceName, String racePassword, String raceTag) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.raceName = raceName;
        this.racePassword = racePassword;
        this.raceTag = raceTag;
    }

    public static RaceCreateDto createDto(LocalDate startedAt, LocalDate endedAt,
                                          String raceName, String racePassword, String raceTag) {
        return new RaceCreateDto(startedAt, endedAt, raceName, racePassword, raceTag);
    }
}
