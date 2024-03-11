package inflearn.mini.workHub.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATE_NAME(BAD_REQUEST, "이미 존재하는 이름입니다."),
    EXIST_MANAGER(BAD_REQUEST, "이미 매니저가 존재합니다."),
    NOT_EXIST_TEAM(BAD_REQUEST, "존재하지 않는 팀입니다."),
    ;


    private final HttpStatus status;
    private final String message;
}
