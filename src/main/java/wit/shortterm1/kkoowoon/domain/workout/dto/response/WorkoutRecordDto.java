package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.dto.CardioDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.DietDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.WeightDto;
import wit.shortterm1.kkoowoon.domain.workout.persist.WorkoutRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutRecordDto {

    @ApiModelProperty(value = "운동 기록 ID", required = true, example = "2")
    private Long workoutRecordId;

    @ApiModelProperty(value = "인증 여부", required = true, example = "true/false")
    private boolean isConfirmed;

    @ApiModelProperty(value = "현재 몸무게", required = false, example = "65.4")
    private double currentWeight;

    @ApiModelProperty(value = "기록 날짜", required = true, example = "2022-07-21")
    private LocalDate recordDate;

    @ApiModelProperty(value = "기록 생성 시간", required = true, example = "2022-07-21T14:23:21")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "기록 생성 일시", required = true, example = "리스트")
    private List<CardioDto> cardioDtoList;

    @ApiModelProperty(value = "기록 생성 일시", required = true, example = "리스트")
    private List<WeightDto> weightDtoList;

    @ApiModelProperty(value = "기록 생성 일시", required = true, example = "리스트")
    private List<DietDto> dietDtoList;

    private WorkoutRecordDto(Long workoutRecordId, boolean isConfirmed,
                             double currentWeight, LocalDate recordDate, LocalDateTime createdAt) {
        this.workoutRecordId = workoutRecordId;
        this.isConfirmed = isConfirmed;
        this.currentWeight = currentWeight;
        this.recordDate = recordDate;
        this.createdAt = createdAt;
        cardioDtoList = new ArrayList<>();
        weightDtoList = new ArrayList<>();
        dietDtoList = new ArrayList<>();
    }

    public static WorkoutRecordDto createDto(WorkoutRecord record) {
        return new WorkoutRecordDto(record.getId(), record.isConfirmed(),
                record.getCurrentWeight(), record.getRecordDate(), record.getCreatedAt());
    }

    public void addCardioDto(CardioDto cardioDto) {
        cardioDtoList.add(cardioDto);
    }

    public void addWeightDto(WeightDto weightDto) {
        weightDtoList.add(weightDto);
    }

    public void addDietDto(DietDto dietDto) {
        dietDtoList.add(dietDto);
    }
}
