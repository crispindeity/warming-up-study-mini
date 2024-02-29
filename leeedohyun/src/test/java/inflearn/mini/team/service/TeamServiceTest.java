package inflearn.mini.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import inflearn.mini.team.domain.Team;
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
        final TeamRegisterRequestDto request = new TeamRegisterRequestDto("팀");

        // when
        teamService.registerTeam(request);

        // then
        verify(teamRepository).save(refEq(request.toEntity()));
    }

    @Test
    void 팀_등록_시_이미_등록된_팀이면_예외가_발생한다() {
        // given
        final TeamRegisterRequestDto request = new TeamRegisterRequestDto("팀");

        doThrow(new RuntimeException("이미 등록된 팀입니다."))
                .when(teamRepository).findByName(anyString());

        // when

        // then
        assertThatThrownBy(() -> teamService.registerTeam(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("이미 등록된 팀입니다.");
    }

    @Test
    void 팀을_조회한다() {
        // given
        final var teams = List.of(new Team("팀1"), new Team("팀2"));

        given(teamRepository.findAll())
                .willReturn(teams);

        // when
        final var result = teamService.getTeams();

        // then
        assertThat(result).hasSize(2);
    }
}
