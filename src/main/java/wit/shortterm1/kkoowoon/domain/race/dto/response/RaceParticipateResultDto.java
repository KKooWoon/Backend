package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceParticipateResultDto {

    @ApiModelProperty(value = "참가 성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "참가자 닉네임", required = true, example = "꾸운닉123")
    private String nickname;

    @ApiModelProperty(value = "레이스 명", required = true, example = "다이어트 레이스!")
    private String raceName;

    @ApiModelProperty(value = "참가 시점", required = true, example = "2021-04-23T23:04:47")
    private LocalDateTime participatedAt;

    public RaceParticipateResultDto(boolean isSuccess, String nickname, String raceName, LocalDateTime participatedAt) {
        this.isSuccess = isSuccess;
        this.nickname = nickname;
        this.raceName = raceName;
        this.participatedAt = participatedAt;
    }

    public static RaceParticipateResultDto createDto(boolean isSuccess, String nickname, String raceName, LocalDateTime participatedAt) {
        return new RaceParticipateResultDto(isSuccess, nickname, raceName, participatedAt);
    }
}
