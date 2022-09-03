package wit.shortterm1.kkoowoon.domain.workout.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.WeightSet;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeightSetDto {

    @ApiModelProperty(value = "세트 ID", required = true, example = "2")
    private Long setId;

    @ApiModelProperty(value = "세트 수(순서)", required = true, example = "3(번째 세트)")
    private int sett;

    @ApiModelProperty(value = "반복 횟수", required = true, example = "8(개)")
    private int reps;

    @ApiModelProperty(value = "무게", required = true, example = "45.5(kg)")
    private double setWeight;

    private WeightSetDto(Long setId, int sett, int reps, double setWeight) {
        this.setId = setId;
        this.sett = sett;
        this.reps = reps;
        this.setWeight = setWeight;
    }

    public static WeightSetDto createDto(WeightSet weightSet) {
        return new WeightSetDto(weightSet.getId(), weightSet.getSett(), weightSet.getReps(), weightSet.getSetWeight());
    }
}