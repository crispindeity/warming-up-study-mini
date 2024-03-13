package inflearn.mini.annualleave.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inflearn.mini.annualleave.domain.AnnualLeave;
import inflearn.mini.annualleave.dto.request.AnnualLeaveRequestDto;
import inflearn.mini.annualleave.dto.request.RemainingAnnualLeaveRequestDto;
import inflearn.mini.annualleave.dto.response.RemainingAnnualLeaveResponseDto;
import inflearn.mini.annualleave.repository.AnnualLeaveRepository;
import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnualLeaveService {

    private final AnnualLeaveRepository annualLeaveRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void requestAnnualLeave(final AnnualLeaveRequestDto request) {
        final Employee findEmployee = getEmployee(request.employeeId());
        findEmployee.useAnnualLeave();
        final AnnualLeave annualLeave = new AnnualLeave(LocalDate.now(), request.annualLeaveDate(), findEmployee);
        annualLeave.validateLeaveRegistrationAdvanceDays(findEmployee.getTeam());
        annualLeaveRepository.save(annualLeave);
    }

    @Transactional(readOnly = true)
    public RemainingAnnualLeaveResponseDto getRemainingAnnualLeave(final RemainingAnnualLeaveRequestDto request) {
        final Employee findEmployee = getEmployee(request.employeeId());
        return new RemainingAnnualLeaveResponseDto(findEmployee.getAnnualLeaveNumber());
    }

    private Employee getEmployee(final Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("등록된 직원이 없습니다."));
    }
}
