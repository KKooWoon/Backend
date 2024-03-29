package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutDeleteResultDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "삭제 시점", required = true, example = "2022-07-21T13:01:43")
    private LocalDateTime deletedAt;

    private WorkoutDeleteResultDto(boolean isSuccess, LocalDateTime deletedAt) {
        this.isSuccess = isSuccess;
        this.deletedAt = deletedAt;
    }

    public static WorkoutDeleteResultDto createDto(boolean isSuccess, LocalDateTime deletedAt) {
        return new WorkoutDeleteResultDto(isSuccess, deletedAt);
    }
}