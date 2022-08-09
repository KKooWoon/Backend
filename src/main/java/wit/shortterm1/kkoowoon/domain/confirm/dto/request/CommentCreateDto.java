package wit.shortterm1.kkoowoon.domain.confirm.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateDto {

    @ApiModelProperty(value = "회원 ID", required = true, example = "3")
    private Long accountId;

    @ApiModelProperty(value = "운동 인증 ID", required = true, example = "2")
    private Long confirmId;

    @ApiModelProperty(value = "댓글 내용", required = true, example = "와 데피니션 미쳤어요!")
    private String description;

}
