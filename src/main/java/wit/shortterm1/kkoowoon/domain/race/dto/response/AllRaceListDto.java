package wit.shortterm1.kkoowoon.domain.race.dto.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllRaceListDto {

    @ApiModelProperty(value = "레이스 참여 개수", required = true, example = "2")
    private int count = 0;

    @ApiModelProperty(value = "현재 참여중인 레이스 리스트", required = true, example = "리스트")
    private CurrentRaceListDto currentRaceListDto;

    @ApiModelProperty(value = "완료한 레이스 리스트", required = true, example = "리스트")
    private PastRaceListDto pastRaceListDto;

    private AllRaceListDto(CurrentRaceListDto currentRaceListDto, PastRaceListDto pastRaceListDto) {
        count = currentRaceListDto.getCount() + pastRaceListDto.getCount();
        this.currentRaceListDto = currentRaceListDto;
        this.pastRaceListDto = pastRaceListDto;
    }

    public static AllRaceListDto createDto(CurrentRaceListDto currentRaceListDto, PastRaceListDto pastRaceListDto) {
        return new AllRaceListDto(currentRaceListDto, pastRaceListDto);
    }
}
