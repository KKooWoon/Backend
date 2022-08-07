package wit.shortterm1.kkoowoon.domain.workout.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCardioDto {

    @ApiModelProperty(value = "유산소 운동 시간(분 단위)", required = true, example = "76(min)")
    private int time;

    @ApiModelProperty(value = "소모 칼로리", required = true, example = "735.4(kcal)")
    private double calorie;

    @ApiModelProperty(value = "유산소 운동 이름", required = true, example = "한강 조깅")
    private String name;

    private CreateCardioDto(int time, double calorie, String name) {
        this.time = time;
        this.calorie = calorie;
        this.name = name;
    }

    public static CreateCardioDto createDto(int time, double calorie, String name) {
        return new CreateCardioDto(time, calorie, name);
    }
}