package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.Diet;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietCreateResultDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "식단 id", required = true, example = "2")
    private Long dietId;

    @ApiModelProperty(value = "생성 시점", required = true, example = "2022-07-21T13:01:43")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "식단 기록 날짜", required = true, example = "2022-07-21")
    private LocalDate date;

    private DietCreateResultDto(boolean isSuccess, Long dietId, LocalDateTime createdAt, LocalDate date) {
        this.isSuccess = isSuccess;
        this.dietId = dietId;
        this.createdAt = createdAt;
        this.date = date;
    }

    public static DietCreateResultDto createDto(boolean isSuccess, Diet diet, LocalDate date) {
        return new DietCreateResultDto(isSuccess, diet.getId(), diet.getCreatedAt(), date);
    }
}
