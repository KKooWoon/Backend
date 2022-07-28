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
import wit.shortterm1.kkoowoon.domain.user.persist.Account;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UnlockRepository unlockRepository;

    public UnlockAvatarListDto getEarnedAvatarList(Long accountId) {
        UnlockAvatarListDto unlockAvatarListDto = UnlockAvatarListDto.createDto();
        unlockRepository.findListByAccountId(accountId)
                .forEach(unlockAvatar -> unlockAvatarListDto.addUnlockAvatar(UnlockAvatarDto.createDto(unlockAvatar.getAvatar(), unlockAvatar)));
        return unlockAvatarListDto;
    }

    @Transactional
    public Long earnNewAvatar(Account account, Avatar avatar) {
        UnlockAvatar newUnlockAvatar = unlockRepository.save(UnlockAvatar.of(account, avatar));
        return newUnlockAvatar.getId();
    }

    public UnlockAvatarDto getLatestEarnedAvatar(Long accountId, int level) {
        UnlockAvatar unlockAvatar = unlockRepository.findByAccountId(accountId, level)
                .orElseThrow(() -> new NoSuchUnlockAvatarException(ErrorCode.NO_SUCH_UNLOCK_AVATAR));
        return UnlockAvatarDto.createDto(unlockAvatar.getAvatar(), unlockAvatar);
    }
}
