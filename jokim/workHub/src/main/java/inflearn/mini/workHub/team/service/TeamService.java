package inflearn.mini.workHub.team.service;

import inflearn.mini.workHub.team.domain.Team;
import inflearn.mini.workHub.team.dto.TeamCreateRequest;
import inflearn.mini.workHub.team.dto.TeamInfoResponse;
import inflearn.mini.workHub.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    @Transactional
    public void registerTeam(TeamCreateRequest request) {
        if(teamRepository.existsByName(request.name())){
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
        teamRepository.save(request.toEntity());
    }

    public List<TeamInfoResponse> getTeamList() {
        List<Team> teamList = teamRepository.findAll();
        return teamList.stream()
                .map(TeamInfoResponse::from)
                .toList();
    }
}
