package inflearn.mini.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.domain.Role;
import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.employee.dto.request.EmployeeWorkHistoryRequest;
import inflearn.mini.employee.dto.response.DateWorkMinutes;
import inflearn.mini.employee.dto.response.EmployeeResponse;
import inflearn.mini.employee.dto.response.EmployeeWorkHistoryResponse;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.service.EmployeeService;
import inflearn.mini.team.domain.Team;
import inflearn.mini.team.exception.TeamNotFoundException;
import inflearn.mini.commute.service.CommuteService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private CommuteService commuteService;

    @Test
    void 직원을_등록한다() throws Exception {
        // given
        final EmployeeRegisterRequestDto request = EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();

        // when
        doNothing().when(employeeService).registerEmployee(any());

        // then
        mockMvc.perform(post("/api/v1/employees/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 직원을_등록할_때_팀이_존재하지_않으면_예외가_발생한다() throws Exception {
        // given
        final EmployeeRegisterRequestDto request = EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();

        // when
        doThrow(new TeamNotFoundException("존재하지 않는 팀입니다."))
                .when(employeeService).registerEmployee(any(EmployeeRegisterRequestDto.class));

        // then
        mockMvc.perform(post("/api/v1/employees/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void 모든_직원을_조회한다() throws Exception {
        // given
        final EmployeeResponse response = EmployeeResponse.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .role(Role.MANAGER)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();

        given(employeeService.getEmployees())
                .willReturn(List.of(response));

        // when
        // then
        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 특정_직원의_날짜별_근무_시간을_조회한다() throws Exception {
        // given
        final Employee employee = Employee.builder()
                .id(1L)
                .name("홍길동")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();
        employee.joinTeam(new Team("개발팀"));

        given(commuteService.getEmployeeDailyWorkingHours(anyLong(), any()))
                .willReturn(new EmployeeWorkHistoryResponse(List.of(
                        new DateWorkMinutes(LocalDate.of(2024, 3, 4), 540, false),
                        new DateWorkMinutes(LocalDate.of(2024, 3, 5), 540, false)
                ), 1080));

        // when

        // then
        mockMvc.perform(get("/api/v1/employees/{employeeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EmployeeWorkHistoryRequest(YearMonth.of(2024, 3)))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 특정_직원의_날짜별_근무_시간을_조회_시_등록되지_않은_직원인_경우_실패한다() throws Exception {
        // given
        given(commuteService.getEmployeeDailyWorkingHours(anyLong(), any()))
                .willThrow(new EmployeeNotFoundException("등록된 직원이 아닙니다."));

        // when

        // then
        mockMvc.perform(get("/api/v1/employees/{employeeId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EmployeeWorkHistoryRequest(YearMonth.of(2024, 3)))))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
