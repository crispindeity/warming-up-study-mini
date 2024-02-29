package org.example.yeonghuns.repository;

import org.example.yeonghuns.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByTeamIdAndRoleIsTrueAndIdNot(long TeamId, long MemberId);

    Optional<Member> findByName(String name);
}
