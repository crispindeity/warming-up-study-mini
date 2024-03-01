package inflearn.mini.team.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import inflearn.mini.team.dto.response.TeamResponseDto;
import inflearn.mini.team.exception.TeamAlreadyExistException;
import inflearn.mini.team.service.TeamService;

@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeamService teamService;

    @Test
    void 팀을_등록한다() throws Exception {
        // given
        final TeamRegisterRequestDto request = new TeamRegisterRequestDto("팀");

        // when
        teamService.registerTeam(request);

        // then
        mockMvc.perform(post("/api/v1/teams/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 팀_등록_시_이미_등록된_팀이면_실패한다() throws Exception {
        // given
        final TeamRegisterRequestDto request = new TeamRegisterRequestDto("팀");

        doThrow(new TeamAlreadyExistException("이미 등록된 팀입니다."))
                .when(teamService).registerTeam(request);

        // when

        // then
        mockMvc.perform(post("/api/v1/teams/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 팀을_조회한다() throws Exception {
        // given
        final var responses = List.of(TeamResponseDto.builder()
                        .name("팀1")
                        .managerName("매니저1")
                        .memberCount(3)
                        .build(),
                TeamResponseDto.builder()
                        .name("팀2")
                        .managerName(null)
                        .memberCount(5)
                        .build());

        given(teamService.getTeams())
                .willReturn(responses);

        // when
        // then
        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
