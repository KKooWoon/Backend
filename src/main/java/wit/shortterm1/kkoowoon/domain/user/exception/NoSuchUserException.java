package wit.shortterm1.kkoowoon.domain.user.exception;

import wit.shortterm1.kkoowoon.global.error.exception.BusinessException;
import wit.shortterm1.kkoowoon.global.error.exception.EntityNotFoundException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class NoSuchUserException extends EntityNotFoundException {
    public NoSuchUserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchUserException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
