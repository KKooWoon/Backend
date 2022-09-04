package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneMonthRecordWithDateDto {

    private LocalDate recordDate;

    private List<WorkoutRecordWithRaceDto> recordWithRaceDtoList;

    private OneMonthRecordWithDateDto(LocalDate recordDate, List<WorkoutRecordWithRaceDto> recordWithRaceDtoList) {
        this.recordDate = recordDate;
        this.recordWithRaceDtoList = recordWithRaceDtoList;
    }

    public static OneMonthRecordWithDateDto createDto(LocalDate recordDate, List<WorkoutRecordWithRaceDto> recordWithRaceDtoList) {
        return new OneMonthRecordWithDateDto(recordDate, recordWithRaceDtoList);
    }
}
