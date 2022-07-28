package wit.shortterm1.kkoowoon.domain.exception;

import wit.shortterm1.kkoowoon.global.error.exception.BusinessException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class NoSuchTokenException extends BusinessException {
    public NoSuchTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
