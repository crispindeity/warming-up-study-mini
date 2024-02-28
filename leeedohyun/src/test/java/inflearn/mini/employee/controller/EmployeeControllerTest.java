package inflearn.mini.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.employee.service.EmployeeService;
import inflearn.mini.team.exception.TeamNotFoundException;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

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
}
