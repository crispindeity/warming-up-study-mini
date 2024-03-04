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
import inflearn.mini.employee.dto.response.EmployeeResponse;
import inflearn.mini.employee.exception.AlreadyAtWorkException;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.service.EmployeeService;
import inflearn.mini.team.domain.Team;
import inflearn.mini.team.exception.TeamNotFoundException;
import inflearn.mini.worktimehistory.service.WorkTimeHistoryService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private WorkTimeHistoryService workTimeHistoryService;

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
    void 출근한다() throws Exception {
        // given
        final Long employeeId = 1L;
        final Employee employee = Employee.builder()
                .id(employeeId)
                .name("홍길동")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();
        employee.joinTeam(new Team("개발팀"));

        // when
        doNothing().when(workTimeHistoryService).goToWork(anyLong());

        // then
        mockMvc.perform(post("/api/v1/employees/{employeeId}/work", employeeId)
                        .param("employeeId", String.valueOf(employeeId)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 출근_시_등록된_직원이_아니면_실패한다() throws Exception {
        // given
        final Long employeeId = 1L;
        final Employee employee = Employee.builder()
                .id(employeeId)
                .name("홍길동")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();
        employee.joinTeam(new Team("개발팀"));

        // when
        doThrow(new EmployeeNotFoundException("등록된 직원이 아닙니다."))
                .when(workTimeHistoryService).goToWork(anyLong());

        // then
        mockMvc.perform(post("/api/v1/employees/{employeeId}/work", employeeId)
                        .param("employeeId", String.valueOf(employeeId)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void 출근_시_이미_출근한_직원인_경우_실패한다() throws Exception {
        // given
        final Long employeeId = 1L;
        final Employee employee = Employee.builder()
                .id(employeeId)
                .name("홍길동")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();
        employee.joinTeam(new Team("개발팀"));

        // when
        doThrow(new AlreadyAtWorkException("이미 출근한 직원입니다."))
                .when(workTimeHistoryService).goToWork(anyLong());

        // then
        mockMvc.perform(post("/api/v1/employees/{employeeId}/work", employeeId)
                        .param("employeeId", String.valueOf(employeeId)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
