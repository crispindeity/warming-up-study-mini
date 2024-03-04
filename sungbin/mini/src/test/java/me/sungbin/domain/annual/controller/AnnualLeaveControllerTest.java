package me.sungbin.domain.annual.controller;

import me.sungbin.domain.annual.model.request.AnnualLeaveRequestDto;
import me.sungbin.domain.employee.entity.Employee;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.annual.controller
 * @fileName : AnnualLeaveControllerTest
 * @date : 3/5/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/5/24       rovert         최초 생성
 */
class AnnualLeaveControllerTest extends BaseControllerTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamRepository teamRepository;

    private Long employeeId;

    @BeforeEach
    void setup() {
        Team team = new Team("개발팀", 7);
        this.teamRepository.save(team);

        Employee employee = new Employee("김철수", false, LocalDate.of(2000, 2, 2));

        employee.updateTeam(team);
        this.employeeRepository.save(employee);
        employee.updateRemainingAnnualLeaves();

        employeeId = employee.getId();
    }

    @Test
    @DisplayName("연차 사용 기능 테스트 - 실패(잘못된 입력값)")
    void apply_annual_leave_test_fail_caused_by_wrong_input() throws Exception {
        AnnualLeaveRequestDto requestDto = new AnnualLeaveRequestDto(employeeId, null);

        this.mockMvc.perform(post("/api/annual-leave")
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
    @DisplayName("연차 사용 기능 테스트 - 실패(존재하지 않는 직원)")
    void apply_annual_leave_test_fail_caused_by_not_exists_employee() throws Exception {
        AnnualLeaveRequestDto requestDto = new AnnualLeaveRequestDto(100L, LocalDate.of(2024, 4, 30));

        this.mockMvc.perform(post("/api/annual-leave")
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
    @DisplayName("연차 사용 기능 테스트 - 실패(팀 정책에 위반된 연차 신청)")
    void apply_annual_leave_test_fail_caused_by_violated_apply_annual_leave() throws Exception {
        AnnualLeaveRequestDto requestDto = new AnnualLeaveRequestDto(employeeId, LocalDate.now().plusDays(2));

        this.mockMvc.perform(post("/api/annual-leave")
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
    @DisplayName("연차 사용 기능 테스트 - 성공")
    void apply_annual_leave_test_success() throws Exception {
        AnnualLeaveRequestDto requestDto = new AnnualLeaveRequestDto(employeeId, LocalDate.now().plusDays(14));

        this.mockMvc.perform(post("/api/annual-leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잔여 연차 조회 테스트 - 실패 (존재하지 않는 직원)")
    void confirm_remained_annual_leave_test_fail_caused_by_not_exists_employee() throws Exception {
        this.mockMvc.perform(get("/api/annual-leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(5L)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잔여 연차 조회 테스트 - 성공")
    void confirm_remained_annual_leave_test_success() throws Exception {
        this.mockMvc.perform(get("/api/annual-leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(employeeId)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
