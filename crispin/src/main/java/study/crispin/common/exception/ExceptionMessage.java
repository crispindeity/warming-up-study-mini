package study.crispin.common.exception;

public enum ExceptionMessage {
    TEAM_NAME_DOES_NOT_EXISTS("존재하지 않는 팀 이름입니다."),
    MEMBER_ALREADY_EXISTS("이미 존재하는 멤버입니다."),
    NOT_MEMBER_OF_TEAM("팀에 소속된 멤버가 아닙니다."),
    ALREADY_MANAGER_REGISTERED("팀에 이미 매니저가 등록되어 있습니다."),
    TEAM_NAME_ALREADY_EXISTS("이미 존재하는 팀 이름 입니다."),
    NOT_AFFILIATED_ANY_TEAM("소속된 팀이 없습니다."),
    ALREADY_CLOCKED_IN("이미 출근 등록이 되어 있습니다."),
    UNREGISTERED_MEMBER("등록되지 않은 멤버입니다."),
    NOT_CLOCKED_IN("츨근등록 되어있지 않습니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
