package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceLeaveResultDto {

    @ApiModelProperty(value = "탈퇴 성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "참가자 닉네임", required = true, example = "꾸운닉123")
    private String nickname;

    @ApiModelProperty(value = "레이스 명", required = true, example = "다이어트 레이스!")
    private String raceName;

    @ApiModelProperty(value = "탈퇴 시점", required = true, example = "2021-04-23T23:04:47")
    private LocalDateTime leaveAt;

    public RaceLeaveResultDto(boolean isSuccess, String nickname, String raceName, LocalDateTime leaveAt) {
        this.isSuccess = isSuccess;
        this.nickname = nickname;
        this.raceName = raceName;
        this.leaveAt = leaveAt;
    }

    public static RaceLeaveResultDto createDto(boolean isSuccess, String nickname, String raceName, LocalDateTime leaveAt) {
        return new RaceLeaveResultDto(isSuccess, nickname, raceName, leaveAt);
    }
}
