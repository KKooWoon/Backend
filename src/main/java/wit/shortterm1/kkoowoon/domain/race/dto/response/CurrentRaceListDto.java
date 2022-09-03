package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentRaceListDto {

    @ApiModelProperty(value = "레이스 info DTO 리스트", required = true, example = "레이스 info DTO 리스트")
    private final List<RaceInfoDto> currentInfoDtoList = new ArrayList<>();

    public static CurrentRaceListDto createDto() {
        return new CurrentRaceListDto();
    }

    public void addRace(RaceInfoDto raceInfoDto) {
        currentInfoDtoList.add(raceInfoDto);
    }
}
