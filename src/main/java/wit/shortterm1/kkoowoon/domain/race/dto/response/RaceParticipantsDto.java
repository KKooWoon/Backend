package wit.shortterm1.kkoowoon.domain.race.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.user.dto.response.UserInfoDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaceParticipantsDto {

    @ApiModelProperty(value = "레이스 ID", required = true, example = "3")
    private Long raceId;

    List<UserInfoDto> userInfoDtoList = new ArrayList<>();

    private RaceParticipantsDto(Long raceId, List<UserInfoDto> userInfoDtoList) {
        this.raceId = raceId;
        this.userInfoDtoList = userInfoDtoList;
    }

    public static RaceParticipantsDto createDto(Long raceId, List<UserInfoDto> userInfoDtoList) {
        return new RaceParticipantsDto(raceId, userInfoDtoList);
    }
}
