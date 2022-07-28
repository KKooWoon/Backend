package wit.shortterm1.kkoowoon.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "잘못된 요청입니다."),
    ENTITY_NOT_FOUND(400, "C003", "개체를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 내부 오류 입니다."),
    INVALID_TYPE_VALUE(400, "C005", "잘못된 타입 입니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "잘못된 접근 입니다."),

    // Account
    USER_ALREADY_EXIST(409, "A001", "이미 존재하는 유저입니다."),
    NO_SUCH_USER(404, "A002", "존재하지 않는 유저입니다."),
    WRONG_PASSWORD(400, "A003", "비밀번호를 정확히 입력해주세요."),

    // Jwt Token
    NO_SUCH_TOKEN(404, "T001", "존재하지 않는 토큰입니다. 다시 확인하세요."),
    TOKEN_EXPIRED(403, "T002", "토큰이 만료되었습니다."),
    INVALID_TOKEN(400, "T003", "유효하지않은 토큰입니다."),
    ;

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    public int status() {
        return status;
    }

    public String message() {
        return message;
    }

    public String code() {
        return code;
    }

}
