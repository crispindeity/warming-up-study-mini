package inflearn.mini.commute.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.exception.AbsentEmployeeException;
import inflearn.mini.employee.exception.AlreadyAtWorkException;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.team.domain.Team;
import inflearn.mini.commute.dto.request.CommutingRequestDto;
import inflearn.mini.commute.dto.request.EndOfWorkRequestDto;
import inflearn.mini.commute.service.CommuteService;

@WebMvcTest(CommuteController.class)
class CommuteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommuteService commuteService;

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
        doNothing().when(commuteService).goToWork(any());

        // then
        mockMvc.perform(post("/api/v1/work", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommutingRequestDto(1L))))
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
                .when(commuteService).goToWork(any());

        // then
        mockMvc.perform(post("/api/v1/work", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CommutingRequestDto(1L))))
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
                .when(commuteService).goToWork(any());

        // then
        mockMvc.perform(post("/api/v1/work", employeeId)
                        .param("employeeId", String.valueOf(employeeId)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 퇴근한다() throws Exception {
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
        doNothing().when(commuteService).leaveWork(any());

        // then
        mockMvc.perform(patch("/api/v1/leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EndOfWorkRequestDto(1L))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 퇴근_시_등록된_직원이_아닌_경우_실패한다() throws Exception {
        // given

        // when
        doThrow(new EmployeeNotFoundException("등록된 직원이 아닙니다."))
                .when(commuteService).leaveWork(any());

        // then
        mockMvc.perform(patch("/api/v1/leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EndOfWorkRequestDto(1L))))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void 퇴근_시_출근한_직원이_아닌_경우_실패한다() throws Exception {
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
        doThrow(new AbsentEmployeeException("출근하지 않은 직원입니다."))
                .when(commuteService).leaveWork(any());

        // then
        mockMvc.perform(patch("/api/v1/leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EndOfWorkRequestDto(1L))))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
