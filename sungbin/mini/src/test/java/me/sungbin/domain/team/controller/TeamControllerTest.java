package me.sungbin.domain.team.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.model.request.RegisterTeamRequestDto;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.annotation.IntegrationTest;
import me.sungbin.global.exception.GlobalExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.team.controller
 * @fileName : TeamControllerTest
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@IntegrationTest
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setup() {
        Team team = new Team("개발팀");
        this.teamRepository.save(team);
    }

    @Test
    @DisplayName("팀 등록 테스트 - 실패 (팀 이름이 공란)")
    void register_team_test_fail_caused_by_team_name_is_empty() throws Exception {
        RegisterTeamRequestDto requestDto = new RegisterTeamRequestDto("");

        this.mockMvc.perform(post("/api/team/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("status").value(GlobalExceptionCode.INVALID_INPUT_VALUE.getHttpStatus().name()))
                .andExpect(jsonPath("code").value(GlobalExceptionCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isNotEmpty())
                .andExpect(jsonPath("timestamp").exists());
    }

    @Test
    @DisplayName("팀 등록 테스트 - 실패 (이미 존재하는 팀)")
    void register_team_test_fail_caused_by_already_exists_team() throws Exception {
        RegisterTeamRequestDto requestDto = new RegisterTeamRequestDto("개발팀");

        this.mockMvc.perform(post("/api/team/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").exists())
                .andExpect(jsonPath("status").value(GlobalExceptionCode.INVALID_INPUT_VALUE.getHttpStatus().name()))
                .andExpect(jsonPath("code").value(GlobalExceptionCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isEmpty())
                .andExpect(jsonPath("timestamp").exists());
    }

    @Test
    @DisplayName("팀 등록 테스트 - 성공")
    void register_team_test_success() throws Exception {
        RegisterTeamRequestDto requestDto = new RegisterTeamRequestDto("디자인팀");

        this.mockMvc.perform(post("/api/team/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
