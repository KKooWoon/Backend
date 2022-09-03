package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleWorkoutRecordDto {

    @ApiModelProperty(value = "운동 기록 ID", required = true, example = "2")
    private Long workoutRecordId;

    @ApiModelProperty(value = "인증 여부", required = true, example = "true/false")
    private boolean isConfirmed;

    @ApiModelProperty(value = "기록 날짜", required = true, example = "2022-07-21")
    private LocalDate recordDate;

    @ApiModelProperty(value = "인증 글 내용", required = true, example = "등 너무 잘 돼서 좋다!")
    private String description;

    @ApiModelProperty(value = "인증 사진", required = true, example = "photo1.png")
    private String photoUrl;

    private SimpleWorkoutRecordDto(Long workoutRecordId, boolean isConfirmed, LocalDate recordDate, String description, String photoUrl) {
        this.workoutRecordId = workoutRecordId;
        this.isConfirmed = isConfirmed;
        this.recordDate = recordDate;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public static SimpleWorkoutRecordDto createDto(WorkoutRecord workoutRecord, String description, String photoUrl) {
        return new SimpleWorkoutRecordDto(workoutRecord.getId(), workoutRecord.isConfirmed(), workoutRecord.getRecordDate(), description, photoUrl);
    }
}
