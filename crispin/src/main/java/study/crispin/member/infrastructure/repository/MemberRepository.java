package study.crispin.member.infrastructure.repository;

import study.crispin.member.domain.Member;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByNameAndBirthdayAndworkStartDate(String name, LocalDate birthday, LocalDate workStartDate);
}
