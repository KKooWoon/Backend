package wit.shortterm1.kkoowoon.domain.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogoutResultDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private LocalDateTime loggedOutAt;

    public LogoutResultDto(boolean isSuccess, LocalDateTime loggedOutAt) {
        this.isSuccess = isSuccess;
        this.loggedOutAt = loggedOutAt;
    }

    public static LogoutResultDto createDto(boolean isSuccess, LocalDateTime loggedOutAt) {
        return new LogoutResultDto(isSuccess, loggedOutAt);
    }

}
