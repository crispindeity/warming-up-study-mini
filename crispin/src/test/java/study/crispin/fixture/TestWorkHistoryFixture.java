package study.crispin.fixture;

import study.crispin.attendance.domain.Attendance;
import study.crispin.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestWorkHistoryFixture {

    public static Attendance 근무_생성(LocalDateTime clockInDateTime, LocalDateTime clockOutDateTime) {
        Member member = TestMemberFixture.멤버_생성(
                1L,
                "테스트멤버1",
                "테스트1팀",
                LocalDate.of(1999, 9, 9),
                LocalDate.of(2024, 2, 29)
        );
        return new Attendance(member, clockInDateTime, clockOutDateTime);
    }
}
