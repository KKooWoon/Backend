package wit.shortterm1.kkoowoon.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wit.shortterm1.kkoowoon.domain.domain.Account;
import wit.shortterm1.kkoowoon.domain.domain.AuthAccount;
import wit.shortterm1.kkoowoon.domain.exception.JwtTokenException;
import wit.shortterm1.kkoowoon.domain.repository.AccountRepository;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class CustomAccountDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String kakaoId) throws UsernameNotFoundException {
        Account account = accountRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new JwtTokenException(ErrorCode.NO_SUCH_TOKEN));
        return new AuthAccount(account);
    }
}
