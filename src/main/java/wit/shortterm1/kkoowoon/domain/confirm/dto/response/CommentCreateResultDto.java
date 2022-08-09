package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateResultDto {

    @ApiModelProperty(value = "댓글 생성 성공 여부", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "댓글 id", required = true, example = "3")
    private Long commentId;

    @ApiModelProperty(value = "셀프 댓글 여부", required = true, example = "true/false")
    private boolean isSelfComment;

    @ApiModelProperty(value = "댓글 생성 시점", required = true, example = "2022-07-18T23:01:00")
    private LocalDateTime createdAt;

    private CommentCreateResultDto(boolean isSuccess, Long commentId, boolean isSelfComment, LocalDateTime createdAt) {
        this.isSuccess = isSuccess;
        this.commentId = commentId;
        this.isSelfComment = isSelfComment;
        this.createdAt = createdAt;
    }

    public static CommentCreateResultDto createDto(boolean isSuccess, Comment comment) {
        return new CommentCreateResultDto(isSuccess, comment.getId(), comment.isSelfComment(), comment.getCreatedAt());
    }
}
