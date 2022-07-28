package wit.shortterm1.kkoowoon.domain.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Objects;

public class AuthAccount extends User {
    private final Account account;

    public AuthAccount(Account account) {
        super(account.getKakaoId(), "PASSWORD", List.of(new SimpleGrantedAuthority(account.getRole())));
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthAccount that = (AuthAccount) o;
        return Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), account);
    }
}
