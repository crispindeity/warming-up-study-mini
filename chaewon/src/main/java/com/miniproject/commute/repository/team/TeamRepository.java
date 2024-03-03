package com.miniproject.commute.repository.team;

import com.miniproject.commute.domain.Team;
import com.miniproject.commute.dto.team.response.TeamMemberCountResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {
    @Query("select new com.miniproject.commute.dto.team.response.TeamMemberCountResponse(t.id, t.name, count(m.id) ) as memberCount from Team t left join t.members m group by t.id, t.name")
    List<TeamMemberCountResponse> findAllTeamAndMemberCount();

}
