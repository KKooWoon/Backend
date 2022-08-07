package wit.shortterm1.kkoowoon.domain.workout.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.dto.WeightSetDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateWeightDto {

    @ApiModelProperty(value = "웨이트 이름", required = true, example = "랫 풀 다운")
    private String name;

    @ApiModelProperty(value = "자극 부위", required = true, example = "등")
    private String body;

    @ApiModelProperty(value = "각 세트 별 내용", required = true, example = "아침")
    private List<WeightSetDto> weightSetList;

}
