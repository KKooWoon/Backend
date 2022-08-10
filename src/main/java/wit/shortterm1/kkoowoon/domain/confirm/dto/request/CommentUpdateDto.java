package wit.shortterm1.kkoowoon.domain.confirm.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateDto {

    @ApiModelProperty(value = "새 댓글 내용", required = true, example = "다시 보니 별로네요...")
    private String description;

}
