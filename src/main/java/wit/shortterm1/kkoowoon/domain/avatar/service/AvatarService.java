package wit.shortterm1.kkoowoon.domain.avatar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wit.shortterm1.kkoowoon.domain.avatar.dto.response.UnlockAvatarDto;
import wit.shortterm1.kkoowoon.domain.avatar.dto.response.UnlockAvatarListDto;
import wit.shortterm1.kkoowoon.domain.avatar.exception.NoSuchUnlockAvatarException;
import wit.shortterm1.kkoowoon.domain.avatar.persist.Avatar;
import wit.shortterm1.kkoowoon.domain.avatar.persist.UnlockAvatar;
import wit.shortterm1.kkoowoon.domain.avatar.repository.AvatarRepository;
import wit.shortterm1.kkoowoon.domain.avatar.repository.UnlockRepository;
import wit.shortterm1.kkoowoon.domain.user.exception.NoSuchUserException;
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.domain.user.repository.AccountRepository;
import wit.shortterm1.kkoowoon.domain.user.service.AccountService;
import wit.shortterm1.kkoowoon.domain.user.service.OauthService;
import wit.shortterm1.kkoowoon.global.common.jwt.JwtProvider;
import wit.shortterm1.kkoowoon.global.common.redis.RedisService;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UnlockRepository unlockRepository;
    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;

    public UnlockAvatarListDto getEarnedAvatarList(Long accountId) {
        UnlockAvatarListDto unlockAvatarListDto = UnlockAvatarListDto.createDto();
        unlockRepository.findListByAccountId(accountId)
                .forEach(unlockAvatar -> unlockAvatarListDto.addUnlockAvatar(UnlockAvatarDto.createDto(unlockAvatar.getAvatar(), unlockAvatar)));
        return unlockAvatarListDto;
    }

    @Transactional
    public Long earnNewAvatar(HttpServletRequest request, Avatar avatar) {
        String kakaoId = jwtProvider.getKakaoId(request);
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new NoSuchUserException(ErrorCode.NO_SUCH_USER));
        UnlockAvatar newUnlockAvatar = unlockRepository.save(UnlockAvatar.of(account, avatar));
        return newUnlockAvatar.getId();
    }

    public UnlockAvatarDto getLatestEarnedAvatar(Long accountId, int level) {
        UnlockAvatar unlockAvatar = unlockRepository.findByAccountId(accountId, level)
                .orElseThrow(() -> new NoSuchUnlockAvatarException(ErrorCode.NO_SUCH_UNLOCK_AVATAR));
        return UnlockAvatarDto.createDto(unlockAvatar.getAvatar(), unlockAvatar);
    }
}
