package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.workout.persist.Weight;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeightUpdateResultDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "운동 기록 id", required = true, example = "12")
    private Long recordId;

    @ApiModelProperty(value = "웨이트 id", required = true, example = "2")
    private Long weightId;

    @ApiModelProperty(value = "업데이트 시점", required = true, example = "2022-07-21T13:01:43")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "웨이트 기록 날짜", required = true, example = "2022-07-21")
    private LocalDate date;

    private WeightUpdateResultDto(boolean isSuccess, Long recordId, Long weightId, LocalDateTime updatedAt, LocalDate date) {
        this.isSuccess = isSuccess;
        this.recordId = recordId;
        this.weightId = weightId;
        this.updatedAt = updatedAt;
        this.date = date;
    }

    public static WeightUpdateResultDto createDto(boolean isSuccess, Weight weight) {
        return new WeightUpdateResultDto(isSuccess, weight.getWorkoutRecord().getId(), weight.getId(),
                weight.getUpdatedAt(), weight.getWorkoutRecord().getRecordDate());
    }
}
