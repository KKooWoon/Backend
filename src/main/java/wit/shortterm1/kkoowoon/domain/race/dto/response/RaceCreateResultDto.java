package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceCreateResultDto {

    @ApiModelProperty(value = "레이스 생성 성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "레이스 id", required = true, example = "3")
    private Long raceId;

    @ApiModelProperty(value = "레이스 생성 시점", required = true, example = "2022-07-18T23:01:00")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "레이스 주인", required = true, example = "꾸운닉123")
    private String owner;

    @ApiModelProperty(value = "레이스 초대코드", required = true, example = "AIKFKD")
    private String raceCode;

    @ApiModelProperty(value = "레이스 비밀번호", required = true, example = "kkoowoon1234")
    private String racePassword;

    @ApiModelProperty(value = "레이스 이름", required = true, example = "다이어트 레이스!")
    private String raceName;

    private RaceCreateResultDto(boolean isSuccess, Long raceId, LocalDateTime createdAt,
                               String owner, String raceCode, String racePassword, String raceName) {
        this.isSuccess = isSuccess;
        this.raceId = raceId;
        this.createdAt = createdAt;
        this.owner = owner;
        this.raceCode = raceCode;
        this.racePassword = racePassword;
        this.raceName = raceName;
    }

    public static RaceCreateResultDto createDto(boolean isSuccess, Long raceId, LocalDateTime createdAt,
                                                String owner, String raceCode, String racePassword, String raceName) {
        return new RaceCreateResultDto(isSuccess, raceId, createdAt, owner, raceCode, racePassword, raceName);
    }
}
