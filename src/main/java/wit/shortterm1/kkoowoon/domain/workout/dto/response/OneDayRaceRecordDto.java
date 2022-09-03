package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayRaceRecordDto {

    private LocalDate date;

    private List<SimpleWorkoutRecordDto> oneDayRaceRecordList;

    private OneDayRaceRecordDto(LocalDate date, List<SimpleWorkoutRecordDto> workoutRecordDtoList) {
        this.date = date;
        this.oneDayRaceRecordList = workoutRecordDtoList;
    }

    public static OneDayRaceRecordDto createDto(LocalDate date, List<SimpleWorkoutRecordDto> workoutRecordDtoList) {
        return new OneDayRaceRecordDto(date, workoutRecordDtoList);
    }
}
