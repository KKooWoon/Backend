package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneMonthRecordDto {

    private Long raceId;

    private List<WorkoutRecordDto> oneMonthRecordList;

    private OneMonthRecordDto(Long raceId, List<WorkoutRecordDto> workoutRecordDtoList) {
        this.raceId = raceId;
        oneMonthRecordList = workoutRecordDtoList;
    }

    public static OneMonthRecordDto createDto(Long raceId, List<WorkoutRecordDto> workoutRecordDtoList) {
        return new OneMonthRecordDto(raceId, workoutRecordDtoList);
    }
}
