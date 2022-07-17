package wit.shortterm1.kkoowoon.domain.exception;

import wit.shortterm1.kkoowoon.global.error.exception.BusinessException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class JwtTokenException extends BusinessException {
    public JwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
