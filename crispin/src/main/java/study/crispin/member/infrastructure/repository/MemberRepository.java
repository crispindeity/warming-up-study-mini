package study.crispin.member.infrastructure.repository;

import study.crispin.member.domain.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByNameAndBirthdayAndWorkStartDate(String name, LocalDate birthday, LocalDate workStartDate);

    List<Member> findAll();

    Long countByTeamName(String name);

    boolean existsByNameAndBirthdayAndWorkStartDate(String name, LocalDate birthday, LocalDate workStartDate);

    Optional<Member> findById(Long memberId);
}
