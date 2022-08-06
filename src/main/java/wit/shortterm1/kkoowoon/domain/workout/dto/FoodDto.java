package wit.shortterm1.kkoowoon.domain.workout.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.Food;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodDto {

    @ApiModelProperty(value = "음식 이름", required = true, example = "닭가슴살")
    private String name;

    @ApiModelProperty(value = "음식 무게", required = true, example = "450.4(g)")
    private double weight;

    @ApiModelProperty(value = "음식 칼로리", required = true, example = "324.8(kcal)")
    private double calorie;

    private FoodDto(String name, double weight, double calorie) {
        this.name = name;
        this.weight = weight;
        this.calorie = calorie;
    }

    public static FoodDto createDto(Food food) {
        return new FoodDto(food.getName(), food.getFoodWeight(), food.getFoodCalorie());
    }
}
