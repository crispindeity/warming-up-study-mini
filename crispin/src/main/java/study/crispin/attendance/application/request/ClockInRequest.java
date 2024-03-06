package study.crispin.attendance.application.request;

public record ClockInRequest(Long memberId) {

    public static ClockInRequest of(Long memberId) {
        return new ClockInRequest(memberId);
    }
}
