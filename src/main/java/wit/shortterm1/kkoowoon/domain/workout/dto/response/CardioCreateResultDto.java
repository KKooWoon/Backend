package wit.shortterm1.kkoowoon.domain.workout.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardioCreateResultDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "유산소 id", required = true, example = "2")
    private Long cardioId;

    @ApiModelProperty(value = "생성 시점", required = true, example = "2022-07-21T13:01:43")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "유산소 운동 기록 날짜", required = true, example = "2022-07-21")
    private LocalDate date;

    private CardioCreateResultDto(boolean isSuccess, Long cardioId, LocalDateTime createdAt, LocalDate date) {
        this.isSuccess = isSuccess;
        this.cardioId = cardioId;
        this.createdAt = createdAt;
        this.date = date;
    }

    public static CardioCreateResultDto createDto(boolean isSuccess, Long cardioId, LocalDateTime createdAt, LocalDate date) {
        return new CardioCreateResultDto(isSuccess, cardioId, createdAt, date);
    }
}