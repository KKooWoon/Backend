package wit.shortterm1.kkoowoon.domain.user.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ApiModel
public class NicknameDuplicateDto {

    @ApiModelProperty(value = "검사한 닉네임", required = true, example = "꾸운닉123")
    private String nickname;

    @ApiModelProperty(value = "중복 여부", required = true, example = "false")
    private boolean isDuplicate;

    @ApiModelProperty(value = "체크 시간", required = false, example = "2022-07-01T13:00:01")
    private LocalDateTime checkedAt;

    private NicknameDuplicateDto(String nickname, boolean isDuplicate, LocalDateTime checkedAt) {
        this.nickname = nickname;
        this.isDuplicate = isDuplicate;
        this.checkedAt = checkedAt;
    }

    public static NicknameDuplicateDto createDto(String nickname, boolean isDuplicate) {
        return new NicknameDuplicateDto(nickname, isDuplicate, LocalDateTime.now());
    }
}
