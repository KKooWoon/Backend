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
    EMPTY_TOKEN_ERROR(400, "C007", "액세스 토큰 정보가 없거나 입력되지 않았습니다."),

    // Account
    USER_ALREADY_EXIST(409, "A001", "이미 존재하는 유저입니다."),
    NO_SUCH_USER(404, "A002", "존재하지 않는 유저입니다."),
    WRONG_PASSWORD(400, "A003", "비밀번호를 정확히 입력해주세요."),

    // Jwt Token
    NO_SUCH_TOKEN(404, "T001", "존재하지 않는 토큰입니다. 다시 확인하세요."),
    TOKEN_EXPIRED(401, "T002", "토큰이 만료되었습니다."),
    INVALID_TOKEN(400, "T003", "유효하지 않은 토큰입니다."),

    // Avatar
    NO_SUCH_UNLOCK_AVATAR(404, "V001", "해당 레벨로 잠금해제된 아바타가 없습니다."),

    // Follow
    NO_SUCH_FOLLOW(404, "F001", "존재하지 않는 팔로우입니다."),
    FOLLOW_ALREADY_EXIST(400, "F002", "이미 존재하는 팔로우입니다."),

    // Race
    TIME_REVERSAL_ERROR(400, "R001", "레이스 시작 일자가 종료 일자보다 늦을 수 없습니다."),
    NO_SUCH_RACE(404, "R002", "존재하지 않는 레이스입니다."),
    WRONG_RACE_PASSWORD(400, "R003", "잘못된 레이스 비밀번호입니다."),
    ALREADY_PARTICIPATE(400, "R004", "이미 해당 레이스에 참여하고 있습니다."),
    NO_SUCH_USER_IN_RACE(400, "R005", "해당 레이스에 존재하지 않는 사용자입니다."),
    OWNER_CANT_LEAVE_RACE(400, "R006", "레이스 방장은 레이스를 탈퇴할 수 없습니다. 레이스 삭제를 진행해주세요."),
    NO_SUCH_PARTICIPATE(404, "R007", "해당 유저가 해당 레이스를 참가한 기록이 존재하지 않습니다."),
    EDIT_RACE_OWNER_ONLY(400, "R008", "레이스는 방장만 삭제할 수 있습니다."),

    // Workout
    SET_EMPTY_ERROR(400, "W001", "이미 세트가 없는 상태입니다."),
    NO_SUCH_WORKOUT_RECORD(404, "W002", "존재하지 않는 운동 기록입니다."),
    NO_SUCH_CARDIO_RECORD(404, "W003", "존재하지 않는 유산소 기록입니다."),
    NO_SUCH_WEIGHT_RECORD(404, "W004", "존재하지 않는 웨이트 기록입니다."),
    NO_SUCH_DIET_RECORD(404, "W005", "존재하지 않는 식단 기록입니다."),
    DELETE_RECORD_MYSELF_ONLY(403, "W006", "내 기록은 나만 지울 수 있습니다."),
    OUT_OF_RACE_DURATION(400, "W007", "운동 기록은 레이스 기간을 벗어날 수 없습니다."),
    NO_SUCH_FOOD(404, "W008", "존재하지 않는 음식 정보입니다."),
    NO_SUCH_SET(404, "W009", "존재하지 않는 세트 정보입니다."),

    // Confirm
    ALREADY_CONFIRMED(400, "CF001", "이미 해당 레이스에 인증된 운동 기록입니다."),
    NO_SUCH_CONFIRMATION(404, "CF002", "존재하지 않는 운동 인증 내역입니다."),
    NO_SUCH_COMMENT(404, "CF003", "존재하지 않는 댓글입니다."),
    EDIT_COMMENT_MYSELF_ONLY(403, "CF004", "댓글은 댓글 작성자 본인만 수정/삭제 가능합니다."),
    YET_CONFIRMED(400, "CF005", "아직 인증되지 않은 운동 기록입니다."),
    WRONG_CONFIRM_OWNER(400, "CF006", "해당 사용자는 해당 인증의 주인이 아닙니다."),

    // Image
    NO_SUCH_IMAGE(404, "I001", "이미지가 존재하지 않습니다."),
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
