package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmDeleteResultDto {

    @ApiModelProperty(value = "레이스 삭제 성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "삭제 시점", required = true, example = "2022-07-18T23:01:00")
    private LocalDateTime deletedAt;

    private ConfirmDeleteResultDto(boolean isSuccess, LocalDateTime deletedAt) {
        this.isSuccess = isSuccess;
        this.deletedAt = deletedAt;
    }

    public static ConfirmDeleteResultDto createDto(boolean isSuccess, LocalDateTime deletedAt) {
        return new ConfirmDeleteResultDto(isSuccess, deletedAt);
    }
}
