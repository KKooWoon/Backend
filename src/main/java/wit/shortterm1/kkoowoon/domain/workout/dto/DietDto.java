package wit.shortterm1.kkoowoon.domain.workout.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.Diet;
import wit.shortterm1.kkoowoon.domain.workout.persist.Food;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietDto {

    @ApiModelProperty(value = "식단 ID", required = true, example = "3")
    private Long dietId;

    @ApiModelProperty(value = "식단 이름", required = true, example = "점심")
    private String name;

    @ApiModelProperty(value = "음식 무게", required = true, example = "450.4(g)")
    private final List<FoodDto> foodDtoList = new ArrayList<>();

    private DietDto(Long dietId, String name, List<Food> foodList) {
        this.dietId = dietId;
        this.name = name;
        foodList.forEach(food -> foodDtoList.add(FoodDto.createDto(food)));
    }

    public static DietDto createDto(Diet diet, List<Food> foodList) {
        return new DietDto(diet.getId(), diet.getName(), foodList);
    }

    public void addFoodDto(FoodDto foodDto) {
        foodDtoList.add(foodDto);
    }
}