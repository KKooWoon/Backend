package wit.shortterm1.kkoowoon.domain.race.exception;

import wit.shortterm1.kkoowoon.global.error.exception.BusinessException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class NoSuchRaceException extends BusinessException {
    public NoSuchRaceException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
