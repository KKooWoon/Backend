package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmCheckResultDto {

    @ApiModelProperty(value = "운동 인증 여부", required = true, example = "true/false")
    private boolean isConfirmed;

    @ApiModelProperty(value = "삭제 시점", required = true, example = "2022-07-18T23:01:00")
    private Long confirmId;

    public ConfirmCheckResultDto(boolean isConfirmed, Long confirmId) {
        this.isConfirmed = isConfirmed;
        this.confirmId = confirmId;
    }

    public static ConfirmCheckResultDto createDto(boolean isConfirmed, Long confirmId) {
        return new ConfirmCheckResultDto(isConfirmed, confirmId);
    }
}
