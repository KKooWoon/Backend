package wit.shortterm1.kkoowoon.domain.workout.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.Weight;
import wit.shortterm1.kkoowoon.domain.workout.persist.WeightSet;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeightDto {

    @ApiModelProperty(value = "웨이트 이름", required = true, example = "랫 풀 다운")
    private String name;

    @ApiModelProperty(value = "자극 부위", required = true, example = "등")
    private String body;

    @ApiModelProperty(value = "각 세트 별 내용", required = true, example = "아침")
    private final List<WeightSetDto> weightSetDtoList = new ArrayList<>();

    private WeightDto(String name, String body, List<WeightSet> weightSetList) {
        this.name = name;
        this.body = body;
        weightSetList.forEach(weightSet -> weightSetDtoList.add(WeightSetDto.createDto(weightSet)));
    }

    public static WeightDto createDto(Weight weight, List<WeightSet> weightSetList) {
        return new WeightDto(weight.getName(), weight.getBody(), weightSetList);
    }
}