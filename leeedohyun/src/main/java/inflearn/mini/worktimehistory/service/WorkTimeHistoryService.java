package inflearn.mini.worktimehistory.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.exception.AbsentEmployeeException;
import inflearn.mini.employee.exception.AlreadyAtWorkException;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.repository.EmployeeRepository;
import inflearn.mini.worktimehistory.domain.WorkTimeHistory;
import inflearn.mini.worktimehistory.repsoitory.WorkTimeHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkTimeHistoryService {

    private final EmployeeRepository employeeRepository;
    private final WorkTimeHistoryRepository workTimeHistoryRepository;

    @Transactional
    public void goToWork(final Long employeeId) {
        final Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("등록된 직원이 아닙니다."));

        if (workTimeHistoryRepository.existsByEmployeeAndWorkEndTimeIsNull(employee)) {
            throw new AlreadyAtWorkException("이미 출근한 직원입니다.");
        }

        final WorkTimeHistory workTimeHistory = new WorkTimeHistory(employee);
        workTimeHistory.goToWork();
        workTimeHistoryRepository.save(workTimeHistory);
    }

    @Transactional
    public void leaveWork(final Long employeeId) {
        final Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("등록된 직원이 아닙니다."));
        final LocalDate now = LocalDate.now();
        final WorkTimeHistory workTimeHistory = workTimeHistoryRepository
                .findByEmployeeAndWorkStartTimeAndAndWorkEndTimeIsNull(employee, now.atStartOfDay(), now.plusDays(1).atStartOfDay())
                .orElseThrow(() -> new AbsentEmployeeException("출근하지 않은 직원입니다."));

        workTimeHistory.leaveWork(LocalDateTime.now());
    }
}
