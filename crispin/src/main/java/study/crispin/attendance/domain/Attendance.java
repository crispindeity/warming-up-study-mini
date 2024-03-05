package study.crispin.attendance.domain;

import study.crispin.member.domain.Member;

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

    public static Attendance clockOut(Attendance attendance, LocalDateTime clockOutDateTime) {
        return new Attendance(
                attendance.id,
                attendance.member,
                attendance.clockInDateTime,
                clockOutDateTime
        );
    }

    public boolean isMatchByMemberId(Long memberId) {
        return this.member.isMatchId(memberId);
    }
}
