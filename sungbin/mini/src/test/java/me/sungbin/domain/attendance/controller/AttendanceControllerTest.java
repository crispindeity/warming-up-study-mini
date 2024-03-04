package me.sungbin.domain.attendance.controller;

import me.sungbin.domain.attendance.model.request.AttendanceCreateClockInRequestDto;
import me.sungbin.domain.attendance.model.request.AttendanceCreateClockOutRequestDto;
import me.sungbin.domain.attendance.model.request.WorkTimeSummaryRequestDto;
import me.sungbin.domain.employee.entity.Employee;
import me.sungbin.domain.employee.repository.EmployeeRepository;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.common.controller.BaseControllerTest;
import me.sungbin.global.exception.GlobalExceptionCode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private Long employeeId;

    @BeforeEach
    void setup() {
        Team team = new Team("개발팀");
        this.teamRepository.save(team);

        Employee employee = new Employee("김철수", false, LocalDate.of(2000, 2, 2));

        employee.updateTeam(team);
        this.employeeRepository.save(employee);

        employeeId = employee.getId();
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
        AttendanceCreateClockInRequestDto requestDto = new AttendanceCreateClockInRequestDto(employeeId);

        this.mockMvc.perform(post("/api/attendance/clock-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("퇴근 기능 테스트 - 실패 (퇴근하려는 직원이 출근하지 않았던 경우)")
    void clock_out_test_fail_caused_by_not_clock_in_employee() throws Exception {
        AttendanceCreateClockOutRequestDto requestDto = new AttendanceCreateClockOutRequestDto(employeeId);

        this.mockMvc.perform(post("/api/attendance/clock-out")
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
    @DisplayName("퇴근 기능 테스트 - 실패 (존재하지 않는 직원)")
    void clock_out_test_fail_caused_by_not_exists_employee() throws Exception {
        clock_in_test_success();

        AttendanceCreateClockOutRequestDto requestDto = new AttendanceCreateClockOutRequestDto(100L);

        this.mockMvc.perform(post("/api/attendance/clock-out")
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
    @DisplayName("퇴근 기능 테스트 - 실패 (퇴근하려는 직원의 id가 null)")
    void clock_out_test_fail_caused_by_not_id_is_null() throws Exception {
        clock_in_test_success();

        AttendanceCreateClockOutRequestDto requestDto = new AttendanceCreateClockOutRequestDto(null);

        this.mockMvc.perform(post("/api/attendance/clock-out")
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
    @DisplayName("퇴근 기능 테스트 - 성공")
    void clock_out_test_success() throws Exception {
        clock_in_test_success();

        AttendanceCreateClockOutRequestDto requestDto = new AttendanceCreateClockOutRequestDto(employeeId);

        this.mockMvc.perform(post("/api/attendance/clock-out")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 직원 근무기록 조회 테스트 - 실패 (잘못된 입력 값)")
    void find_specific_employee_attendance_record_test_fail_caused_by_wrong_input() throws Exception {
        clock_out_test_success();

        WorkTimeSummaryRequestDto requestDto = new WorkTimeSummaryRequestDto(employeeId, "");

        this.mockMvc.perform(get("/api/attendance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("employeeId", String.valueOf(requestDto.employeeId()))
                        .param("date", requestDto.date()))
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
    @DisplayName("특정 직원 근무기록 조회 테스트 - 실패 (존재하지 않는 달)")
    void find_specific_employee_attendance_record_test_fail_caused_by_not_exists_month() throws Exception {
        clock_out_test_success();

        WorkTimeSummaryRequestDto requestDto = new WorkTimeSummaryRequestDto(employeeId, "2024-13");

        this.mockMvc.perform(get("/api/attendance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("employeeId", String.valueOf(requestDto.employeeId()))
                        .param("date", requestDto.date()))
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
    @DisplayName("특정 직원 근무기록 조회 테스트 - 실패 (존재하지 않는 직원)")
    void find_specific_employee_attendance_record_test_fail_caused_by_not_exists_employee() throws Exception {
        clock_out_test_success();

        WorkTimeSummaryRequestDto requestDto = new WorkTimeSummaryRequestDto(100L, "2024-02");

        this.mockMvc.perform(get("/api/attendance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("employeeId", String.valueOf(requestDto.employeeId()))
                        .param("date", requestDto.date()))
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
    @DisplayName("특정 직원 근무기록 조회 테스트 - 성공")
    void find_specific_employee_attendance_record_test_success() throws Exception {
        clock_out_test_success();

        WorkTimeSummaryRequestDto requestDto = new WorkTimeSummaryRequestDto(employeeId, "2024-02");

        this.mockMvc.perform(get("/api/attendance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("employeeId", String.valueOf(requestDto.employeeId()))
                        .param("date", requestDto.date()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("details").exists())
                .andExpect(jsonPath("sum").exists());
    }
}
