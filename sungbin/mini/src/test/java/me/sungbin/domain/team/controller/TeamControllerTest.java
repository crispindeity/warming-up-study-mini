package me.sungbin.domain.team.controller;

import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.model.request.RegistrationTeamRequestDto;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.common.controller.BaseControllerTest;
import me.sungbin.global.exception.GlobalExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

class TeamControllerTest extends BaseControllerTest {

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setup() {
        Team team = new Team("개발팀", 7);
        this.teamRepository.save(team);
    }

    @Test
    @DisplayName("팀 등록 테스트 - 실패 (팀 이름이 공란)")
    void register_team_test_fail_caused_by_team_name_is_empty() throws Exception {
        RegistrationTeamRequestDto requestDto = new RegistrationTeamRequestDto("");

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
        RegistrationTeamRequestDto requestDto = new RegistrationTeamRequestDto("개발팀");

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
        RegistrationTeamRequestDto requestDto = new RegistrationTeamRequestDto("디자인팀");

        this.mockMvc.perform(post("/api/team/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("팀 정보 조회 테스트 - 성공")
    void find_team_info_test_success() throws Exception {
        this.mockMvc.perform(get("/api/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
