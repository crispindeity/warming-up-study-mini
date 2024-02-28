package inflearn.mini.employee.service;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.employee.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void 직원을_등록한다() {
        // given
        final EmployeeRegisterRequestDto request = EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();

        // when
        employeeService.registerEmployee(request);

        // then
        verify(employeeRepository).save(refEq(request.toEntity()));
    }
}
