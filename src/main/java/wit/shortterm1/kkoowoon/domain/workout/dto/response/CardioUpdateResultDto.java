package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardioUpdateResultDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "운동기록 id", required = true, example = "12")
    private Long recordId;

    @ApiModelProperty(value = "유산소 id", required = true, example = "2")
    private Long cardioId;

    @ApiModelProperty(value = "업데이트 시점", required = true, example = "2022-07-21T13:01:43")
    private LocalDateTime updatedAt;

    private CardioUpdateResultDto(boolean isSuccess, Long recordId, Long cardioId, LocalDateTime updatedAt) {
        this.isSuccess = isSuccess;
        this.recordId = recordId;
        this.cardioId = cardioId;
        this.updatedAt = updatedAt;
    }

    public static CardioUpdateResultDto createDto(boolean isSuccess, Long recordId, Long cardioId, LocalDateTime updatedAt) {
        return new CardioUpdateResultDto(isSuccess, recordId, cardioId, updatedAt);
    }
}