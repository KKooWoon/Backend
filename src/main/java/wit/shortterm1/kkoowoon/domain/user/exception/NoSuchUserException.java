package wit.shortterm1.kkoowoon.domain.user.exception;

import wit.shortterm1.kkoowoon.global.error.exception.BusinessException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class NoSuchUserException extends BusinessException {
    public NoSuchUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
