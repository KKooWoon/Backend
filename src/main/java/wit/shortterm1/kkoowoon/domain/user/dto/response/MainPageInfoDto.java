package wit.shortterm1.kkoowoon.domain.user.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowingListDto;
import wit.shortterm1.kkoowoon.domain.race.dto.response.CurrentRaceListDto;
import wit.shortterm1.kkoowoon.domain.workout.dto.response.WorkoutRecordDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainPageInfoDto {

    @ApiModelProperty(value = "성공 여부", required = true, example = "true/false")
    private UserInfoDto userInfoDto;

    @ApiModelProperty(value = "운동 기록 Dto", required = true, example = "")
    private WorkoutRecordDto workoutRecordDto;

    @ApiModelProperty(value = "팔로잉 리스트 Dto", required = true, example = "")
    private FollowingListDto followingListDto;

    @ApiModelProperty(value = "현재 참여중인 레이스 리스트 Dto", required = true, example = "")
    private CurrentRaceListDto currentRaceListDto;

    private MainPageInfoDto(UserInfoDto userInfoDto, WorkoutRecordDto workoutRecordDto,
                           FollowingListDto followingListDto, CurrentRaceListDto currentRaceListDto) {
        this.userInfoDto = userInfoDto;
        this.workoutRecordDto = workoutRecordDto;
        this.followingListDto = followingListDto;
        this.currentRaceListDto = currentRaceListDto;
    }

    public static MainPageInfoDto createDto(UserInfoDto userInfoDto, WorkoutRecordDto workoutRecordDto,
                                            FollowingListDto followingListDto, CurrentRaceListDto currentRaceListDto) {
        return new MainPageInfoDto(userInfoDto, workoutRecordDto, followingListDto, currentRaceListDto);
    }
}
