package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmCheckResultDto {

    @ApiModelProperty(value = "레이스 삭제 성공 여부", required = true, example = "true/false")
    private boolean isExist;

    @ApiModelProperty(value = "삭제 시점", required = true, example = "2022-07-18T23:01:00")
    private Long confirmId;

    public ConfirmCheckResultDto(boolean isExist, Long confirmId) {
        this.isExist = isExist;
        this.confirmId = confirmId;
    }

    public static ConfirmCheckResultDto createDto(boolean isExist, Long confirmId) {
        return new ConfirmCheckResultDto(isExist, confirmId);
    }
}
