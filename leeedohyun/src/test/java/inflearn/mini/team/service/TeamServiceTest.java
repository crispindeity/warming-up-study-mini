package inflearn.mini.team.service;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import inflearn.mini.team.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    void 팀을_등록한다() {
        // given
        final TeamRegisterRequestDto request = new TeamRegisterRequestDto("팀 이름");

        // when
        teamService.registerTeam(request);

        // then
        verify(teamRepository).save(refEq(request.toEntity()));
    }
}
