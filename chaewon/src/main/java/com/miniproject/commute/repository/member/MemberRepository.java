package com.miniproject.commute.repository.member;

import com.miniproject.commute.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    public Member getByTeamIdAndIsManager(long teamId, boolean isManager);
    public boolean existsByTeamIdAndIsManager(long teamId, boolean isManager);
    @Query("select m from Member m join fetch m.team t")
    public List<Member> findAllWithTeam();

    @Query("SELECT m.name FROM Member m WHERE m.team.id = :teamId AND m.isManager = true")
    Optional<String> findManagerByTeamId(@Param("teamId") Long teamId);

}
