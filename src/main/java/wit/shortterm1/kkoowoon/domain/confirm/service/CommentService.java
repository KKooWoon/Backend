package wit.shortterm1.kkoowoon.domain.confirm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.confirm.dto.request.CommentCreateDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.request.CommentUpdateDto;
import wit.shortterm1.kkoowoon.domain.confirm.dto.response.*;
import wit.shortterm1.kkoowoon.domain.confirm.exception.NoSuchCommentException;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Comment;
import wit.shortterm1.kkoowoon.domain.confirm.persist.Confirm;
import wit.shortterm1.kkoowoon.domain.confirm.repository.CommentRepository;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.service.AccountService;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final ConfirmService confirmService;
    private final AccountService accountService;

    @Transactional
    public CommentCreateResultDto createComment(Long accountId, Long confirmId, CommentCreateDto commentCreateDto) {
        Confirm confirm = confirmService.getConfirm(confirmId);
        Account account = accountService.getAccount(accountId);
        Comment savedComment = commentRepository.save(Comment.of(commentCreateDto.getDescription(), isSelfComment(confirm, account), confirm, account));
        return CommentCreateResultDto.createDto(true, savedComment);
    }

    @Transactional
    public CommentDeleteResultDto deleteComment(Long accountId, Long commentId) {
        Comment comment = getComment(commentId);
        checkCommentOwner(accountId, comment);
        commentRepository.delete(comment);
        return CommentDeleteResultDto.createDto(true, LocalDateTime.now());
    }

    @Transactional
    public CommentUpdateResultDto updateComment(Long accountId, Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment comment = getComment(commentId);

        checkCommentOwner(accountId, comment);

        comment.updateDescription(commentUpdateDto.getDescription());
        Comment updatedComment = commentRepository.save(comment);
        return CommentUpdateResultDto.createDto(true, updatedComment);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchCommentException(ErrorCode.NO_SUCH_COMMENT));
    }

    private boolean isSelfComment(Confirm confirm, Account account) {
        return account.equals(confirm.getWorkoutRecord().getAccount());
    }

    private void checkCommentOwner(Long accountId, Comment comment) {
        if (!comment.getAccount().getId().equals(accountId)) {
            throw new DeleteAuthException(ErrorCode.EDIT_COMMENT_MYSELF_ONLY);
        }
    }

    public CommentInfoDto findComment(Long commentId) {
        Comment comment = getComment(commentId);
        return CommentInfoDto.createDto(comment);
    }

    public CommentInfoListDto findCommentList(Long confirmId) {
        CommentInfoListDto commentInfoListDto = CommentInfoListDto.createDto();
        commentRepository.findListById(confirmId)
                .forEach(comment -> commentInfoListDto.addCommentInfoDto(CommentInfoDto.createDto(comment)));
        return commentInfoListDto;
    }
}
