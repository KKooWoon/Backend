package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateResultDto {

    @ApiModelProperty(value = "댓글 생성 성공 여", required = true, example = "true/false")
    private boolean isSuccess;

    @ApiModelProperty(value = "댓글 id", required = true, example = "3")
    private Long commentId;

    @ApiModelProperty(value = "셀프 댓글 여부", required = true, example = "true/false")
    private boolean selfComment;

    @ApiModelProperty(value = "댓글 수정 시점", required = true, example = "2022-07-18T23:01:00")
    private LocalDateTime updatedAt;

    private CommentUpdateResultDto(boolean isSuccess, Long commentId, boolean selfComment, LocalDateTime updatedAt) {
        this.isSuccess = isSuccess;
        this.commentId = commentId;
        this.selfComment = selfComment;
        this.updatedAt = updatedAt;
    }

    public static CommentUpdateResultDto createDto(boolean isSuccess, Comment updatedComment) {
        return new CommentUpdateResultDto(isSuccess, updatedComment.getId(), updatedComment.isSelfComment(), updatedComment.getUpdatedAt());
    }
}
