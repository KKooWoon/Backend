package wit.shortterm1.kkoowoon.domain.workout.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.Cardio;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardioDto {

    @ApiModelProperty(value = "유산소 ID", required = true, example = "2")
    private Long cardioId;

    @ApiModelProperty(value = "유산소 이름", required = true, example = "한강 걷기")
    private String name;

    @ApiModelProperty(value = "운동 시간", required = true, example = "76(분)")
    private int duration;

    @ApiModelProperty(value = "소모 칼로리", required = true, example = "324.8(kcal)")
    private double calorie;

    private CardioDto(Long cardioId, String name, int duration, double calorie) {
        this.cardioId = cardioId;
        this.name = name;
        this.duration = duration;
        this.calorie = calorie;
    }

    public static CardioDto createDto(Cardio cardio) {
        return new CardioDto(cardio.getId(), cardio.getName(), cardio.getDuration(), cardio.getCalorie());
    }
}