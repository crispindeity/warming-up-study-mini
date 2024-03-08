package me.sungbin.domain.employee.controller;

import me.sungbin.domain.employee.model.request.RegistrationEmployeeRequestDto;
import me.sungbin.domain.employee.repository.EmployeeRepository;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.common.controller.BaseControllerTest;
import me.sungbin.global.exception.GlobalExceptionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.controller
 * @fileName : EmployeeControllerTest
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

class EmployeeControllerTest extends BaseControllerTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        Team team = new Team("개발팀", 7);
        this.teamRepository.save(team);
    }

    @Test
    @DisplayName("직원 등록 테스트 - 실패 (잘못된 입력 값)")
    void register_employee_test_fail_caused_by_wrong_input() throws Exception {
        RegistrationEmployeeRequestDto requestDto = new RegistrationEmployeeRequestDto("", "", false, LocalDate.of(1996, 5, 22));

        this.mockMvc.perform(post("/api/employee/register")
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
    @DisplayName("직원 등록 테스트 - 실패 (존재하지 않는 팀에 등록)")
    void register_employee_test_fail_caused_by_register_not_exists_team() throws Exception {
        RegistrationEmployeeRequestDto requestDto = new RegistrationEmployeeRequestDto("장그래", "영업팀", false, LocalDate.of(1992, 2, 22));

        this.mockMvc.perform(post("/api/employee/register")
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
    @DisplayName("직원 등록 테스트 - 성공")
    void register_employee_test_success() throws Exception {
        RegistrationEmployeeRequestDto requestDto = new RegistrationEmployeeRequestDto("양성빈", "개발팀", false, LocalDate.of(1996, 5, 22));

        this.mockMvc.perform(post("/api/employee/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("직원 정보 조회 테스트 - 성공")
    void find_employees_info_test_success() throws Exception {
        this.mockMvc.perform(get("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("초과근무 조회 테스트 - 성공")
    void find_overtime_work_test_fail_caused_by_not_exists_year_and_month() throws Exception {
        this.mockMvc.perform(get("/api/employee/overtime")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("date", "2024-03"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
