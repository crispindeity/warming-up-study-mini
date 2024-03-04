package me.sungbin.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.attendance.entity.Attendance;
import me.sungbin.domain.attendance.model.request.AttendanceCreateClockInRequestDto;
import me.sungbin.domain.attendance.model.request.AttendanceCreateClockOutRequestDto;
import me.sungbin.domain.attendance.model.request.WorkTimeSummaryRequestDto;
import me.sungbin.domain.attendance.model.response.WorkTimeDetail;
import me.sungbin.domain.attendance.model.response.WorkTimeSummaryResponseDto;
import me.sungbin.domain.attendance.repository.AttendanceRepository;
import me.sungbin.domain.employee.entity.Employee;
import me.sungbin.domain.employee.repository.EmployeeRepository;
import me.sungbin.global.exception.custom.AlreadyAttendanceClockInException;
import me.sungbin.global.exception.custom.AlreadyAttendanceClockOutException;
import me.sungbin.global.exception.custom.AttendanceNotFoundException;
import me.sungbin.global.exception.custom.EmployeeNotFoundException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.service
 * @fileName : AttendanceService
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final EmployeeRepository employeeRepository;

    /**
     * 출근 기록을 생성하는 메소드입니다.
     * @param requestDto 출근 기록 생성 요청 데이터 전달 객체(DTO)
     */
    @Transactional
    public void clockIn(AttendanceCreateClockInRequestDto requestDto) {
        // 요청된 직원 ID로 직원을 조회하고, 존재하지 않으면 예외를 발생시킵니다.
        Employee employee = this.employeeRepository.findById(requestDto.employeeId())
                .orElseThrow(EmployeeNotFoundException::new);

        // 오늘의 날짜 범위를 가져옵니다.
        Pair<LocalDateTime, LocalDateTime> todayRange = getTodayDateTimeRange();

        // 해당 직원의 오늘 출근 기록이 이미 존재하는지 확인합니다.
        boolean existingAttendance = attendanceRepository
                .findByEmployeeAndClockInTimeBetween(employee, todayRange.getFirst(), todayRange.getSecond())
                .isPresent();

        // 이미 출근 기록이 있다면 예외를 발생시킵니다.
        if (existingAttendance) {
            throw new AlreadyAttendanceClockInException("이미 출근하셨습니다.");
        }

        Attendance attendance = requestDto.toEntity(employee);
        attendance.clockIn();
        this.attendanceRepository.save(attendance);
    }

    /**
     * 퇴근기록을 생성하는 메소드입니다.
     * @param requestDto 퇴근 기록 생성 요청 데이터 전달 객체(DTO)
     */
    @Transactional
    public void clockOut(AttendanceCreateClockOutRequestDto requestDto) {
        // 요청된 직원 ID로 직원을 조회하고, 존재하지 않으면 예외를 발생시킵니다.
        Employee employee = this.employeeRepository.findById(requestDto.employeeId())
                .orElseThrow(EmployeeNotFoundException::new);

        // 오늘의 날짜 범위를 가져옵니다.
        Pair<LocalDateTime, LocalDateTime> todayDateTimeRange = getTodayDateTimeRange();

        // 해당 직원의 오늘 날짜 기준 최근 출근 기록을 조회합니다.
        Attendance attendance = this.attendanceRepository
                .findByEmployeeAndClockInTimeBetween(employee, todayDateTimeRange.getFirst(), todayDateTimeRange.getSecond())
                .orElseThrow(() -> new AttendanceNotFoundException("오늘의 출근 기록이 없습니다."));


        // 이미 퇴근 기록이 있다면 예외를 발생시킵니다.
        if (attendance.getClockOutTime() != null) {
            throw new AlreadyAttendanceClockOutException("이미 퇴근 처리가 되었습니다.");
        }

        // 퇴근 시간 기록
        attendance.clockOut();
    }

    public WorkTimeSummaryResponseDto calculateMonthlyWorkTime(WorkTimeSummaryRequestDto requestDto) {
        // 직원 존재 여부 확인
        Employee employee = this.employeeRepository.findById(requestDto.employeeId())
                .orElseThrow(EmployeeNotFoundException::new);

        // 해당 직원의 요청된 연도와 월에 대한 출퇴근 기록을 가져옵니다.
        List<Attendance> attendances =
                this.attendanceRepository.findByEmployeeIdAndMonthAndYear(employee.getId(),
                        convertStringToLocalDate(requestDto).getYear(), convertStringToLocalDate(requestDto).getMonthValue());

        // 날짜별로 출퇴근 기록을 그룹화합니다.
        Map<LocalDate, List<Attendance>> groupedByDate = attendances.stream()
                .collect(Collectors.groupingBy(a -> a.getClockInTime().toLocalDate()));

        // 날짜별 근무 시간을 계산합니다.
        return calculateWorkTimeDetail(groupedByDate, convertStringToLocalDate(requestDto));
    }

    /**
     * 현재 날짜의 시작과 끝을 구합니다.
     * @return
     */
    private Pair<LocalDateTime, LocalDateTime> getTodayDateTimeRange() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
        return Pair.of(startOfDay, endOfDay);
    }

    /**
     * 요청된 문자열 날짜를 LocalDate 객체로 변환합니다.
     * @param requestDto
     * @return
     */
    private LocalDate convertStringToLocalDate(WorkTimeSummaryRequestDto requestDto) {
        String dateStr = requestDto.date() + "-01";
        return LocalDate.parse(dateStr);
    }

    /**
     * 날짜별 상세 근무 시간과 총합을 계산합니다.
     * @param groupedByDate
     * @param localDate
     * @return
     */
    private WorkTimeSummaryResponseDto calculateWorkTimeDetail(Map<LocalDate, List<Attendance>> groupedByDate, LocalDate localDate) {
        List<WorkTimeDetail> details = new ArrayList<>();
        long sum = 0;

        for (LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), 1); date.getMonthValue() == localDate.getMonthValue(); date = date.plusDays(1)) {
            long dailyWorkingMinutes = Optional.ofNullable(groupedByDate.get(date))
                    .flatMap(list -> list.stream().filter(a -> a.getClockOutTime() != null)
                            .findFirst()
                            .map(a -> ChronoUnit.MINUTES.between(a.getClockInTime(), a.getClockOutTime())))
                    .orElse(0L);

            details.add(new WorkTimeDetail(date, dailyWorkingMinutes));
            sum += dailyWorkingMinutes;
        }

        return new WorkTimeSummaryResponseDto(details, sum);
    }

}
