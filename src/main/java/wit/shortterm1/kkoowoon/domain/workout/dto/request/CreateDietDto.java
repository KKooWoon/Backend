package wit.shortterm1.kkoowoon.domain.workout.dto.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.dto.FoodDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateDietDto {

    @ApiModelProperty(value = "식단 이름", required = true, example = "아침")
    @NotNull
    private String name;

    @ApiModelProperty(value = "음식 리스트", required = true, example = "[FoodDto1, FoodDto2, ...]")
    @NotNull
    private List<FoodDto> foodList;

}