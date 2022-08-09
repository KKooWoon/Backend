package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfoListDto {

    @ApiModelProperty(value = "댓글 정보 DTO 리스트", required = true, example = "[ { CommentInfoDto1 }, { CommentInfoDto2 }, ... ]")
    private final List<CommentInfoDto> commentInfoDtoList = new ArrayList<>();

    public static CommentInfoListDto createDto() {
        return new CommentInfoListDto();
    }

    public void addCommentInfoDto(CommentInfoDto commentInfoDto) {
        commentInfoDtoList.add(commentInfoDto);
    }
}
