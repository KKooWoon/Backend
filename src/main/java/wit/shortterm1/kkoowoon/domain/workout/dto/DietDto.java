package wit.shortterm1.kkoowoon.domain.workout.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.Diet;

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
    private List<FoodDto> foodDtoList;

    private DietDto(Long dietId, String name) {
        this.dietId = dietId;
        this.name = name;
        foodDtoList = new ArrayList<>();
    }

    public static DietDto createDto(Diet diet) {
        return new DietDto(diet.getId(), diet.getName());
    }

    public void addFoodDto(FoodDto foodDto) {
        foodDtoList.add(foodDto);
    }
}
