package wit.shortterm1.kkoowoon.global.error.exception;

public class EmptyTokenException extends BusinessException {
    public EmptyTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
