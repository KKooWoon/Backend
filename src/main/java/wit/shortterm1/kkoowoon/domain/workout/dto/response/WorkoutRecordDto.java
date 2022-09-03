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

    @ApiModelProperty(value = "유산소 DTO 리스트", required = true, example = "리스트")
    private List<CardioDto> cardioDtoList;

    @ApiModelProperty(value = "웨이트 DTO 리스트", required = true, example = "리스트")
    private List<WeightDto> weightDtoList;

    @ApiModelProperty(value = "다이어트 DTO 리스트", required = true, example = "리스트")
    private List<DietDto> dietDtoList;

    private WorkoutRecordDto(Long workoutRecordId, boolean isConfirmed, double currentWeight, LocalDate recordDate, LocalDateTime createdAt,
                            List<CardioDto> cardioDtoList, List<WeightDto> weightDtoList, List<DietDto> dietDtoList) {
        this.workoutRecordId = workoutRecordId;
        this.isConfirmed = isConfirmed;
        this.currentWeight = currentWeight;
        this.recordDate = recordDate;
        this.createdAt = createdAt;
        this.cardioDtoList = cardioDtoList;
        this.weightDtoList = weightDtoList;
        this.dietDtoList = dietDtoList;
    }

    public static WorkoutRecordDto createDto(WorkoutRecord record) {
        if (record == null) {
            return null;
        }
        List<WeightDto> weightDtoList = new ArrayList<>();
        List<CardioDto> cardioDtoList = new ArrayList<>();
        List<DietDto> dietDtoList = new ArrayList<>();

        record.getWeightList()
                .forEach(weight -> weightDtoList.add(WeightDto.createDto(weight, weight.getWeightSetList())));
        record.getCardioList().forEach(cardio -> cardioDtoList.add(CardioDto.createDto(cardio)));
        record.getDietList().forEach(diet -> dietDtoList.add(DietDto.createDto(diet, diet.getFoodList())));

        return new WorkoutRecordDto(record.getId(), record.isConfirmed(),
                record.getCurrentWeight(), record.getRecordDate(), record.getCreatedAt(),
                cardioDtoList, weightDtoList, dietDtoList);
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