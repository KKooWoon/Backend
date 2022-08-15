package wit.shortterm1.kkoowoon.domain.race.dto.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceUpdateDto {

    @ApiModelProperty(value = "레이스 시작 일자", required = true, example = "2022-07-17")
    @NotNull
    private LocalDate startedAt;

    @ApiModelProperty(value = "레이스 종료 일자", required = true, example = "2022-07-25")
    @NotNull
    private LocalDate endedAt;

    @ApiModelProperty(value = "레이스 이름", required = true, example = "다이어트 아니고 웨이트 레이스")
    @NotNull
    private String raceName;

    @ApiModelProperty(value = "레이스 비밀번호", required = true, example = "kkoowoon1234")
    @NotNull
    private String racePassword;

    @ApiModelProperty(value = "레이스 해시태그", required = true, example = "#다이어트")
    @NotNull
    private String raceTag;

    @ApiModelProperty(value = "레이스 주인 닉네임", required = true, example = "꾸운닉123")
    @NotNull
    private String ownerNickname;

    private RaceUpdateDto(LocalDate startedAt, LocalDate endedAt,
                          String raceName, String racePassword, String raceTag, String ownerNickname) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.raceName = raceName;
        this.racePassword = racePassword;
        this.raceTag = raceTag;
        this.ownerNickname = ownerNickname;
    }

    public static RaceUpdateDto createDto(LocalDate startedAt, LocalDate endedAt,
                                          String raceName, String racePassword, String raceTag, String ownerNickname) {
        return new RaceUpdateDto(startedAt, endedAt, raceName, racePassword, raceTag, ownerNickname);
    }
}
