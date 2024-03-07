package inflearn.mini.workHub.team.service;

import inflearn.mini.workHub.global.CustomException;
import inflearn.mini.workHub.team.domain.Team;
import inflearn.mini.workHub.team.dto.TeamCreateRequest;
import inflearn.mini.workHub.team.dto.TeamInfoResponse;
import inflearn.mini.workHub.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static inflearn.mini.workHub.global.ErrorCode.DUPLICATE_NAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    @Transactional
    public void registerTeam(TeamCreateRequest request) {
        if(teamRepository.existsByName(request.name())){
            throw new CustomException(DUPLICATE_NAME);
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
