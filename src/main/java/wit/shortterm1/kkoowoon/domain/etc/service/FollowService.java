package wit.shortterm1.kkoowoon.domain.etc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.*;
import wit.shortterm1.kkoowoon.domain.etc.exception.AlreadyFollowException;
import wit.shortterm1.kkoowoon.domain.etc.exception.NoSuchFollowException;
import wit.shortterm1.kkoowoon.domain.etc.persist.Follow;
import wit.shortterm1.kkoowoon.domain.etc.repository.FollowRepository;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.service.AccountService;
import wit.shortterm1.kkoowoon.global.common.jwt.JwtProvider;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    @Transactional
    public FollowResultDto makeFollow(HttpServletRequest request, Long targetId) {
        Account sourceAccount = accountService.getAccountByKakaoId(jwtProvider.getKakaoId(request));
        Account targetAccount = accountService.getAccount(targetId);
        followRepository.findFollowByTwo(sourceAccount.getId(), targetAccount.getId()).ifPresent(follow -> {
            throw new AlreadyFollowException(ErrorCode.FOLLOW_ALREADY_EXIST);
        });
        followRepository.save(Follow.of(sourceAccount, targetAccount));
        return FollowResultDto.createDto(sourceAccount, targetAccount, true, true);
    }

    @Transactional
    public FollowResultDto unfollow(HttpServletRequest request, Long targetId) {
        Account sourceAccount = accountService.getAccountByKakaoId(jwtProvider.getKakaoId(request));
        Account targetAccount = accountService.getAccount(targetId);
        Follow follow = followRepository.findFollowByTwo(sourceAccount.getId(), targetAccount.getId())
                .orElseThrow(() -> new NoSuchFollowException(ErrorCode.NO_SUCH_FOLLOW));
        followRepository.delete(follow);
        return FollowResultDto.createDto(sourceAccount, targetAccount, false, true);
    }

    public FollowerListDto getFollowerList(Long accountId) {
        Account account = accountService.getAccount(accountId);
        FollowerListDto followerListDto = FollowerListDto.createDto();
        followRepository.findFollowerListByTarget(account)
                .forEach(follow -> followerListDto.addFollower(FollowInfoDto.createDto(follow.getSource(), account)));
        return followerListDto;
    }

    public FollowingListDto getFollowingList(Long accountId) {
        Account account = accountService.getAccount(accountId);
        FollowingListDto followingListDto = FollowingListDto.createDto();
        followRepository.findFollowingListBySource(account)
                .forEach(follow -> followingListDto.addFollowing(FollowInfoDto.createDto(account, follow.getFollowing())));
        return followingListDto;
    }

    public FollowOrNotDto getFollowOrNot(Long sourceId, Long targetId) {
        AtomicReference<FollowOrNotDto> followOrNotDto = new AtomicReference<>();
        followRepository.findFollowByTwo(sourceId, targetId).ifPresentOrElse(
                follow -> followOrNotDto.set(FollowOrNotDto.createDto(sourceId, targetId, false)),
                () -> followOrNotDto.set(FollowOrNotDto.createDto(sourceId, targetId, true)));
        return followOrNotDto.get();
    }
}
