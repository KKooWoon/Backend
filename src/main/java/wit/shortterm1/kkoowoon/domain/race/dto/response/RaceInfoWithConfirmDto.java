package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.race.persist.Race;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceInfoWithConfirmDto {

    @ApiModelProperty(value = "운동 인증 여부", required = true, example = "true false")
    private boolean isComplete;

    @ApiModelProperty(value = "운동 인증 여부", required = true, example = "true false")
    private RaceInfoDto raceInfoDto;

    private RaceInfoWithConfirmDto(RaceInfoDto raceInfoDto, boolean isComplete) {
        this.isComplete = isComplete;
        this.raceInfoDto = raceInfoDto;
    }

    public static RaceInfoWithConfirmDto createDto(Race race, boolean isComplete) {
        return new RaceInfoWithConfirmDto(RaceInfoDto.createDto(race), isComplete);
    }
}
