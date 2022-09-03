package wit.shortterm1.kkoowoon.domain.race.dto.request;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceCreateDto {

    @ApiModelProperty(value = "레이스 시작 일자", required = true, example = "2022-07-17")
    private LocalDate startedAt;

    @ApiModelProperty(value = "레이스 종료 일자", required = true, example = "2022-07-25")
    private LocalDate endedAt;

    @ApiModelProperty(value = "레이스 이름", required = true, example = "다이어트 레이스!")
    private String raceName;

    @ApiModelProperty(value = "레이스 비밀번호", required = true, example = "kkoowoon1234")
    private String racePassword;

    @ApiModelProperty(value = "레이스 해시태그", required = true, example = "#다이어트")
    private String raceTag;

    @ApiModelProperty(value = "레이스 주인 닉네임", required = true, example = "꾸운닉123")
    private String ownerNickname;

    @ApiModelProperty(value = "레이스 소개글", required = true, example = "다이터트 전문 방입니다.")
    private String description;

    private RaceCreateDto(LocalDate startedAt, LocalDate endedAt, String raceName, String racePassword,
                         String raceTag, String ownerNickname, String description) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.raceName = raceName;
        this.racePassword = racePassword;
        this.raceTag = raceTag;
        this.ownerNickname = ownerNickname;
        this.description = description;
    }

    public static RaceCreateDto createDto(LocalDate startedAt, LocalDate endedAt, String raceName,
                                          String racePassword, String raceTag, String ownerNickname, String description) {
        return new RaceCreateDto(startedAt, endedAt, raceName, racePassword, raceTag, ownerNickname, description);
    }
}
