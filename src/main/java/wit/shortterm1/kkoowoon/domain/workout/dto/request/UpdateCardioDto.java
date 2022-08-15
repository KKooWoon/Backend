package wit.shortterm1.kkoowoon.domain.workout.dto.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCardioDto {

    @ApiModelProperty(value = "유산소 운동 시간(분 단위)", required = true, example = "76(min)")
    @NotNull
    private int time;

    @ApiModelProperty(value = "소모 칼로리", required = true, example = "735.4(kcal)")
    @NotNull
    private double calorie;

    @ApiModelProperty(value = "유산소 운동 이름", required = true, example = "한강 조깅")
    @NotNull
    private String name;

    private UpdateCardioDto(int time, double calorie, String name) {
        this.time = time;
        this.calorie = calorie;
        this.name = name;
    }

    public static UpdateCardioDto createDto(int time, double calorie, String name) {
        return new UpdateCardioDto(time, calorie, name);
    }
}