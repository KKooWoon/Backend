package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmCreateResultDto {

    @ApiModelProperty(value = "레이스 인증 성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "레이스 인증 id", required = true, example = "3")
    private Long confirmId;

    @ApiModelProperty(value = "인증 시점", required = true, example = "2022-07-18T23:01:00")
    private LocalDateTime createdAt;

    private ConfirmCreateResultDto(boolean isSuccess, Long confirmId, LocalDateTime createdAt) {
        this.isSuccess = isSuccess;
        this.confirmId = confirmId;
        this.createdAt = createdAt;
    }

    public static ConfirmCreateResultDto createDto(boolean isSuccess, Confirm confirm) {
        return new ConfirmCreateResultDto(isSuccess, confirm.getId(), confirm.getCreatedAt());
    }
}
