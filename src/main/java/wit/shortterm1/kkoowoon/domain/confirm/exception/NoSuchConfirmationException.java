package wit.shortterm1.kkoowoon.domain.confirm.exception;

import wit.shortterm1.kkoowoon.global.error.exception.BusinessException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class NoSuchConfirmationException extends BusinessException {

    public NoSuchConfirmationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NoSuchConfirmationException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
