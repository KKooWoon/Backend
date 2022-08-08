package wit.shortterm1.kkoowoon.domain.etc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowInfoDto;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowResultDto;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowerListDto;
import wit.shortterm1.kkoowoon.domain.etc.dto.response.FollowingListDto;
import wit.shortterm1.kkoowoon.domain.etc.exception.AlreadyFollowException;
import wit.shortterm1.kkoowoon.domain.etc.exception.NoSuchFollowException;
import wit.shortterm1.kkoowoon.domain.etc.persist.Follow;
import wit.shortterm1.kkoowoon.domain.etc.repository.FollowRepository;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.user.service.AccountService;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountService accountService;

    @Transactional
    public FollowResultDto makeFollow(Long sourceId, Long targetId) {
        Account sourceAccount = accountService.getAccount(sourceId);
        Account targetAccount = accountService.getAccount(targetId);
        followRepository.findFollowByTwo(sourceAccount, targetAccount).ifPresent(follow -> {
            throw new AlreadyFollowException(ErrorCode.FOLLOW_ALREADY_EXIST);
        });
        followRepository.save(Follow.of(sourceAccount, targetAccount));
        return FollowResultDto.createDto(sourceAccount, targetAccount, true, true);
    }

    @Transactional
    public FollowResultDto unfollow(Long sourceId, Long targetId) {
        Account sourceAccount = accountService.getAccount(sourceId);
        Account targetAccount = accountService.getAccount(targetId);
        Follow follow = followRepository.findFollowByTwo(sourceAccount, targetAccount)
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
}
