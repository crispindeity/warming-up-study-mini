package inflearn.mini.team.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inflearn.mini.team.domain.Team;
import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import inflearn.mini.team.dto.response.TeamResponseDto;
import inflearn.mini.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void registerTeam(final TeamRegisterRequestDto request) {
        teamRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<TeamResponseDto> getTeams() {
        final List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(TeamResponseDto::from)
                .toList();
    }
}
