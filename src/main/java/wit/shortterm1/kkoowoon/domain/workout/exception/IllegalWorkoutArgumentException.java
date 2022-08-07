package wit.shortterm1.kkoowoon.domain.workout.exception;

import wit.shortterm1.kkoowoon.global.error.exception.BusinessException;
import wit.shortterm1.kkoowoon.global.error.exception.ErrorCode;

public class IllegalWorkoutArgumentException extends BusinessException {
    public IllegalWorkoutArgumentException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}