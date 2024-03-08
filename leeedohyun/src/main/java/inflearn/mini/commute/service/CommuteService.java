package inflearn.mini.commute.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inflearn.mini.employee.dto.request.EmployeeWorkHistoryRequest;
import inflearn.mini.employee.dto.response.DateWorkMinutes;
import inflearn.mini.employee.dto.response.EmployeeWorkHistoryResponse;
import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.exception.AbsentEmployeeException;
import inflearn.mini.employee.exception.AlreadyAtWorkException;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.employee.repository.EmployeeRepository;
import inflearn.mini.commute.dto.request.CommutingRequestDto;
import inflearn.mini.commute.domain.Commute;
import inflearn.mini.commute.dto.request.EndOfWorkRequestDto;
import inflearn.mini.commute.repsoitory.CommuteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommuteService {

    private final EmployeeRepository employeeRepository;
    private final CommuteRepository commuteRepository;

    @Transactional
    public void goToWork(final CommutingRequestDto commutingRequestDto) {
        final Employee employee = employeeRepository.findById(commutingRequestDto.employeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("등록된 직원이 아닙니다."));

        if (commuteRepository.existsByEmployeeAndWorkEndTimeIsNull(employee)) {
            throw new AlreadyAtWorkException("이미 출근한 직원입니다.");
        }

        final Commute commute = new Commute(employee);
        commute.goToWork();
        commuteRepository.save(commute);
    }

    @Transactional
    public void leaveWork(final EndOfWorkRequestDto request) {
        final Employee employee = employeeRepository.findById(request.employeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("등록된 직원이 아닙니다."));
        final LocalDate now = LocalDate.now();
        final Commute commute = commuteRepository
                .findWorkTimeHistoryForDate(employee, now.atStartOfDay(), now.plusDays(1).atStartOfDay())
                .orElseThrow(() -> new AbsentEmployeeException("출근하지 않은 직원입니다."));

        commute.leaveWork(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public EmployeeWorkHistoryResponse getEmployeeDailyWorkingHours(final Long employeeId,
                                                                    final EmployeeWorkHistoryRequest request) {
        final Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("등록된 직원이 아닙니다."));

        final LocalDateTime start = LocalDateTime.of(request.year(), request.month(), 1, 0, 0);
        final LocalDateTime end = LocalDateTime.of(request.getEndOfMonth(), LocalTime.of(23, 59, 59));

        final List<Commute> workTimeHistories = commuteRepository
                .findAllByEmployeeAndWorkStartTimeBetween(employee, start, end);

        final List<DateWorkMinutes> detail = getDateWorkHours(workTimeHistories);
        final long sum = calculateSumWorkHour(workTimeHistories);

        return new EmployeeWorkHistoryResponse(detail, sum);
    }

    private List<DateWorkMinutes> getDateWorkHours(final List<Commute> workTimeHistories) {
        return workTimeHistories.stream()
                .map(workTimeHistory -> DateWorkMinutes.of(workTimeHistory.getWorkStartDate(),
                        workTimeHistory.calculateWorkingHours()))
                .toList();
    }

    private long calculateSumWorkHour(final List<Commute> workTimeHistories) {
        return workTimeHistories.stream()
                .mapToLong(Commute::calculateWorkingHours)
                .sum();
    }
}
