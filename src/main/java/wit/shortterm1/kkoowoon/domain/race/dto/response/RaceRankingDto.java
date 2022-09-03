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
public class RaceRankingDto {

    @ApiModelProperty(value = "레이스 ID", required = true, example = "3")
    private Long raceId;

    @ApiModelProperty(value = "레이스 랭킹에 사용되는 점수를 포함한 간단한 유저 정보 리스트", required = true, example = "List<UserInfoWithScoreDto>")
    List<UserInfoWithScoreDto> userInfoWithScoreDtoList = new ArrayList<>();

    private RaceRankingDto(Long raceId, List<UserInfoWithScoreDto> userInfoWithScoreDtoList) {
        this.raceId = raceId;
        this.userInfoWithScoreDtoList = userInfoWithScoreDtoList;
    }

    public static RaceRankingDto createDto(Long raceId, List<UserInfoWithScoreDto> userInfoWithScoreDtoList) {
        return new RaceRankingDto(raceId, userInfoWithScoreDtoList);
    }
}
