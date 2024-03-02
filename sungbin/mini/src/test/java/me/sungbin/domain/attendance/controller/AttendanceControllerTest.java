package me.sungbin.domain.attendance.controller;

import me.sungbin.domain.attendance.entity.Attendance;
import me.sungbin.domain.attendance.model.request.AttendanceCreateClockInRequestDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.controller
 * @fileName : AttendanceControllerTest
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */
class AttendanceControllerTest extends BaseControllerTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setup() {
        Team team = new Team("개발팀");
        this.teamRepository.save(team);

        Employee employee = new Employee("김철수", false, LocalDate.of(2000, 2, 2));

        employee.updateTeam(team);
        this.employeeRepository.save(employee);
    }

    @Test
    @DisplayName("출근 기능 테스트 - 실패 (id가 null)")
    void clock_in_test_fail_caused_by_id_is_null() throws Exception {
        AttendanceCreateClockInRequestDto requestDto = new AttendanceCreateClockInRequestDto(null);

        this.mockMvc.perform(post("/api/attendance/clock-in")
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
    @DisplayName("출근 기능 테스트 - 실패 (존재하지 않는 employeeId)")
    void clock_in_test_fail_caused_by_not_exists_id() throws Exception {
        AttendanceCreateClockInRequestDto requestDto = new AttendanceCreateClockInRequestDto(100L);

        this.mockMvc.perform(post("/api/attendance/clock-in")
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
    @DisplayName("출근 기능 테스트 - 실패 (이미 출근을 했을 경우)")
    void clock_in_test_fail_caused_by_already_clock_in() throws Exception {
        Long employeeId = 1L; // setup 메서드에서 생성한 직원의 ID는 1L이라고 가정
        AttendanceCreateClockInRequestDto firstRequestDto = new AttendanceCreateClockInRequestDto(employeeId);

        this.mockMvc.perform(post("/api/attendance/clock-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstRequestDto)))
                .andExpect(status().isOk());

        AttendanceCreateClockInRequestDto secondRequestDto = new AttendanceCreateClockInRequestDto(employeeId);
        this.mockMvc.perform(post("/api/attendance/clock-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secondRequestDto)))
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
    @DisplayName("출근 기능 테스트 - 성공")
    void clock_in_test_success() throws Exception {
        AttendanceCreateClockInRequestDto requestDto = new AttendanceCreateClockInRequestDto(1L);

        this.mockMvc.perform(post("/api/attendance/clock-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
