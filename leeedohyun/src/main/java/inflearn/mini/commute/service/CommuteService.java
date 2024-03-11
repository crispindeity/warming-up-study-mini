package inflearn.mini.commute.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inflearn.mini.annualleave.repository.AnnualLeaveRepository;
import inflearn.mini.commute.domain.Commute;
import inflearn.mini.commute.dto.request.CommutingRequestDto;
import inflearn.mini.commute.dto.request.EndOfWorkRequestDto;
import inflearn.mini.commute.repsoitory.CommuteRepository;
import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.dto.request.EmployeeWorkHistoryRequest;
import inflearn.mini.employee.dto.response.DateWorkMinutes;
import inflearn.mini.employee.dto.response.EmployeeWorkHistoryResponse;
import inflearn.mini.employee.exception.AbsentEmployeeException;
import inflearn.mini.employee.exception.AlreadyAtWorkException;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommuteService {

    private final AnnualLeaveRepository annualLeaveRepository;
    private final EmployeeRepository employeeRepository;
    private final CommuteRepository commuteRepository;

    @Transactional
    public void goToWork(final CommutingRequestDto commutingRequestDto) {
        final Employee employee = getEmployee(commutingRequestDto.employeeId());

        if (commuteRepository.existsByEmployeeAndWorkEndTimeIsNull(employee)) {
            throw new AlreadyAtWorkException("이미 출근한 직원입니다.");
        }

        final Commute commute = new Commute(employee);
        commute.goToWork();
        commuteRepository.save(commute);
    }

    @Transactional
    public void leaveWork(final EndOfWorkRequestDto request) {
        final Employee employee = getEmployee(request.employeeId());
        final LocalDate now = LocalDate.now();
        final Commute commute = commuteRepository
                .findWorkTimeHistoryForDate(employee, now.atStartOfDay(), now.plusDays(1).atStartOfDay())
                .orElseThrow(() -> new AbsentEmployeeException("출근하지 않은 직원입니다."));

        commute.leaveWork(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public EmployeeWorkHistoryResponse getEmployeeDailyWorkingHours(final Long employeeId,
                                                                    final EmployeeWorkHistoryRequest request) {
        final Employee employee = getEmployee(employeeId);
        final List<DateWorkMinutes> detail = getDateWorkMinutes(request, employee);
        final long sum = calculateSumWorkHour(detail);
        return new EmployeeWorkHistoryResponse(detail, sum);
    }

    private Employee getEmployee(final Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("등록된 직원이 아닙니다."));
    }

    private List<DateWorkMinutes> getDateWorkMinutes(final EmployeeWorkHistoryRequest request,
                                                     final Employee employee) {
        final List<DateWorkMinutes> detail = new ArrayList<>();
        for (int i = 1; i <= request.getEndOfMonth().getDayOfMonth(); i++) {
            final LocalDate day = request.yearMonth().atDay(i);
            addWorkDetailForDate(employee, day, detail);
        }
        return detail;
    }

    private void addWorkDetailForDate(final Employee employee,
                                      final LocalDate day,
                                      final List<DateWorkMinutes> detail) {
        final boolean isUsedAnnualLeave = annualLeaveRepository.existsByEmployeeAndUseDate(employee, day);
        if (isUsedAnnualLeave) {
            detail.add(new DateWorkMinutes(day, 0, true));
            return;
        }
        final Commute commute = commuteRepository.findByEmployeeAndWorkStartTimeBetween(employee, day.atStartOfDay(),
                        day.atTime(23, 59, 59)).get();
        detail.add(new DateWorkMinutes(day, commute.calculateWorkingHours(), false));
    }

    private long calculateSumWorkHour(final List<DateWorkMinutes> detail) {
        return detail.stream()
                .mapToLong(DateWorkMinutes::workMinutes)
                .sum();
    }
}
