package study.crispin.attendance.application.request;

public record ClockInOrOutRequest(Long memberId) {

    public static ClockInOrOutRequest of(Long memberId) {
        return new ClockInOrOutRequest(memberId);
    }
}
