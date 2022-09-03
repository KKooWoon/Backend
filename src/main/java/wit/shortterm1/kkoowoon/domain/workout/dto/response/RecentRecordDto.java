package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecentRecordDto {

    @ApiModelProperty(value = "최근 운동 기록 리스트", required = true, example = "List<SimpleWorkoutRecordDto>")
    private List<SimpleWorkoutRecordDto> recentRecordList;

    private RecentRecordDto(List<SimpleWorkoutRecordDto> workoutRecordDtoList) {
        this.recentRecordList = workoutRecordDtoList;
    }

    public static RecentRecordDto createDto(List<SimpleWorkoutRecordDto> workoutRecordDtoList) {
        return new RecentRecordDto(workoutRecordDtoList);
    }
}
