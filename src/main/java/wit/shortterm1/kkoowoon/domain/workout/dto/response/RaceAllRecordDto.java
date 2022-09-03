package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceAllRecordDto {

    private Long raceId;

    private List<OneDayRaceRecordDto> oneDayRecords;

    private RaceAllRecordDto(Long raceId, List<OneDayRaceRecordDto> oneDayRecords) {
        this.raceId = raceId;
        this.oneDayRecords = oneDayRecords;
    }

    public static RaceAllRecordDto createDto(Long raceId, List<OneDayRaceRecordDto> oneDayRecords) {
        return new RaceAllRecordDto(raceId, oneDayRecords);
    }
}
