package study.crispin.attendance.domain;

import study.crispin.member.domain.Member;

import java.time.Duration;
import java.time.LocalDateTime;

public record Attendance(Long id, Member member, LocalDateTime clockInDateTime, LocalDateTime clockOutDateTime) {

    public Attendance(Member member, LocalDateTime clockInDateTime, LocalDateTime clockOutDateTime) {
        this(null, member, clockInDateTime, clockOutDateTime);
    }

    public static Attendance clockIn(Member member, LocalDateTime clockInDateTime) {
        return new Attendance(member, clockInDateTime, null);
    }

    public static Attendance of(Long id, Member member, LocalDateTime clockInDateTime) {
        return new Attendance(id, member, clockInDateTime, null);
    }

    public static Attendance of(Long id, Member member, LocalDateTime clockInDateTime, LocalDateTime clockOutDateTime) {
        return new Attendance(id, member, clockInDateTime, clockOutDateTime);
    }

    public static Attendance clockOut(Attendance attendance, LocalDateTime clockOutDateTime) {
        return new Attendance(
                attendance.id,
                attendance.member,
                attendance.clockInDateTime,
                clockOutDateTime
        );
    }

    public boolean isClockInAndOut(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return isMemberMatch(memberId) &&
                !isClockOutNull() &&
                isBetweenClockInDates(startDate, endDate);
    }

    public boolean isClockIn(Long memberId, LocalDateTime startDate, LocalDateTime endDate) {
        return isMemberMatch(memberId) &&
                isClockOutNull() &&
                isBetweenClockInDates(startDate, endDate);
    }

    private boolean isClockOutNull() {
        return clockOutDateTime == null;
    }

    public boolean isMemberMatch(Long memberId) {
        return this.member.isMatchId(memberId);
    }

    private boolean isBetweenClockInDates(LocalDateTime startDate, LocalDateTime endDate) {
        return clockInDateTime.isAfter(startDate) && clockInDateTime.isBefore(endDate);
    }

    public Long calculateWorkHour() {
        return Duration.between(clockInDateTime, clockOutDateTime).toMinutes();
    }
}
