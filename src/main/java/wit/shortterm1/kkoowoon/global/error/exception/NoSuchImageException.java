package wit.shortterm1.kkoowoon.global.error.exception;

import java.util.NoSuchElementException;

public class NoSuchImageException extends NoSuchElementException {

    private final ErrorCode errorCode;

    public NoSuchImageException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NoSuchImageException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
