package org.example.yeonghuns.config.Error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E1", "올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E2", "잘못된 HTTP 메서드를 호출했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E3", "서버 에러가 발생했습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "E4", "존재하지 않는 대상입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M1", "해당 멤버는 존재하지 않습니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "T1", "해당 팀은 존재하지 않습니다."),
    TEAM_ALREADY_EXISTS(HttpStatus.NOT_FOUND, "T2", "이미 동일한 이름의 팀이 존재하고 있습니다.")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}
