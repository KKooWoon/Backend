package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceDeleteResultDto {

    @ApiModelProperty(value = "레이스 삭제 성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "레이스 삭제 시점", required = true, example = "2022-07-18T23:01:00")
    private LocalDateTime deletedAt;

    @ApiModelProperty(value = "레이스 주인", required = true, example = "꾸운닉123")
    private String owner;

    @ApiModelProperty(value = "레이스 코드", required = true, example = "AIKFKD")
    private String raceCode;

    @ApiModelProperty(value = "레이스 이름", required = true, example = "다이어트 레이스!")
    private String raceName;

    private RaceDeleteResultDto(boolean isSuccess, LocalDateTime deletedAt,
                                String owner, String raceCode, String raceName) {
        this.isSuccess = isSuccess;
        this.deletedAt = deletedAt;
        this.owner = owner;
        this.raceCode = raceCode;
        this.raceName = raceName;
    }

    public static RaceDeleteResultDto createDto(boolean isSuccess, LocalDateTime deletedAt,
                                                String owner, String raceCode, String raceName) {
        return new RaceDeleteResultDto(isSuccess, deletedAt, owner, raceCode, raceName);
    }
}
