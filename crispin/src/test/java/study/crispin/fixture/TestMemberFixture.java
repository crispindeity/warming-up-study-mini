package study.crispin.fixture;

import study.crispin.member.domain.Member;

import java.time.LocalDate;

public class TestMemberFixture {
    public static Member 멤버_생성(Long id, String name, String teamName, LocalDate birthday, LocalDate workStartDate) {
        return Member.of(id, name, teamName, birthday, workStartDate);
    }
}
