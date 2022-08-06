package wit.shortterm1.kkoowoon.domain.workout.dto.request;

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
    private String name;

    @ApiModelProperty(value = "음식 리스트", required = true,
            example = "\"foodList\": [\n" +
            "      {\n" +
            "          \"calorie\" : 100.3,\n" +
            "          \"name\" : \"닭가슴살\",\n" +
            "          \"weight\" : 34.5\n" +
            "      },\n" +
            "      {\n" +
            "          \"calorie\" : 34.6,\n" +
            "          \"name\" : \"프로틴 음료\",\n" +
            "          \"weight\" : 250\n" +
            "      }      \n" +
            "  ]")
    private List<FoodDto> foodList;

}
