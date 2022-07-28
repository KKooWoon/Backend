package wit.shortterm1.kkoowoon.domain.exception;

import wit.shortterm1.kkoowoon.global.error.exception.BusinessException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class UserDuplicateException extends BusinessException {
    public UserDuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
