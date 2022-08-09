package wit.shortterm1.kkoowoon.domain.confirm.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Comment;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfoDto {

    @ApiModelProperty(value = "댓글 주인 ID", required = true, example = "1")
    private Long accountId;

    @ApiModelProperty(value = "댓글 주인 닉네임", required = true, example = "꾸운닉123")
    private String nickname;

    @ApiModelProperty(value = "댓글 내용", required = true, example = "데피니션 미쳤다!")
    private String description;

    @ApiModelProperty(value = "최종 수정 일자", required = true, example = "2022-07-30T12:31:22")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "수정 여부", required = true, example = "true/false")
    private boolean isUpdated;

    @ApiModelProperty(value = "셀프 댓글 여부", required = true, example = "true/false")
    private boolean isSelfCommented;

    private CommentInfoDto(Long accountId, String nickname, String description, LocalDateTime updatedAt, boolean isUpdated, boolean isSelfCommented) {
        this.accountId = accountId;
        this.nickname = nickname;
        this.description = description;
        this.updatedAt = updatedAt;
        this.isUpdated = isUpdated;
        this.isSelfCommented = isSelfCommented;
    }

    public static CommentInfoDto createDto(Comment comment) {
        return (comment.getUpdatedAt() != null)
                ? new CommentInfoDto(comment.getAccount().getId(), comment.getAccount().getNickname(), comment.getDescription(),
                                        comment.getUpdatedAt(), true, comment.isSelfComment())
                : new CommentInfoDto(comment.getAccount().getId(), comment.getAccount().getNickname(), comment.getDescription(),
                                        comment.getCreatedAt(), false, comment.isSelfComment());
    }
}
