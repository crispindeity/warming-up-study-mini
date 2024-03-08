package me.sungbin.domain.employee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sungbin.domain.attendance.entity.Attendance;
import me.sungbin.domain.attendance.repository.AttendanceRepository;
import me.sungbin.domain.employee.entity.Employee;
import me.sungbin.domain.employee.model.request.EmployeesInfoResponseDto;
import me.sungbin.domain.employee.model.request.RegistrationEmployeeRequestDto;
import me.sungbin.domain.employee.model.response.OvertimeResponseDto;
import me.sungbin.domain.employee.repository.EmployeeRepository;
import me.sungbin.domain.team.entity.Team;
import me.sungbin.domain.team.repository.TeamRepository;
import me.sungbin.global.exception.custom.AlreadyExistsManagerException;
import me.sungbin.global.exception.custom.TeamNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.service
 * @fileName : EmployeeService
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final TeamRepository teamRepository;

    private final AttendanceRepository attendanceRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Value("${api.secret.key}")
    private String key;

    @Transactional
    public void registerEmployee(RegistrationEmployeeRequestDto requestDto) {
        Employee employee = requestDto.toEntity();

        Team team = this.teamRepository.findByName(requestDto.teamName()).orElseThrow(TeamNotFoundException::new);

        // 매니저가 이미 존재하는 경우 예외 발생
        if (employee.isManager() && team.hasManager()) {
            throw new AlreadyExistsManagerException("이미 매니저가 해당 팀에 존재합니다.");
        }

        this.employeeRepository.save(employee);
        employee.updateRemainingAnnualLeaves();

        team.addEmployee(employee);
        this.teamRepository.save(team);
    }

    public List<EmployeesInfoResponseDto> findEmployeesInfo() {
        List<Employee> employees = this.employeeRepository.findAll();

        return employees.stream().map(EmployeesInfoResponseDto::new).toList();
    }

    public List<OvertimeResponseDto> calculateOvertimeHours(YearMonth date) {
        // 연/월 분석 및 해당 월의 총 근무일 계산 (공휴일 제외)
        int totalWorkingDays = calculateWorkingDays(date);

        // 기준 근무 시간 계산 (분 단위)
        int standardWorkingMinutes = totalWorkingDays * 8 * 60; // 추후 리팩토링

        System.out.println("standardWorkingMinutes = " + standardWorkingMinutes);

        List<OvertimeResponseDto> results = new ArrayList<>();

        List<Employee> employees = this.employeeRepository.findAll();

        for (Employee employee : employees) {
            // 직원별 총 근무시간 계산 (분 단위)
            int totalWorkMinutes = calculateTotalWorkMinutesForEmployee(employee, date);

            System.out.println(totalWorkMinutes);

            // 초과근무 시간 계산
            int overtimeMinutes = totalWorkMinutes - standardWorkingMinutes;

            if (overtimeMinutes < 0) {
                overtimeMinutes = 0;
            }

            // 결과 리스트 추가
            results.add(new OvertimeResponseDto(employee.getId(), employee.getName(), overtimeMinutes));
        }

        return results;
    }

    public void exportOverTimeRecordsToCSV(List<OvertimeResponseDto> responseDtoList, String fileName) {
        CSVFormat csv = CSVFormat.DEFAULT.builder()
                .setHeader("ID", "이름", "초과 근무 시간(분)", "비고")
                .build();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
             CSVPrinter csvPrinter = new CSVPrinter(writer, csv)) {
            for (OvertimeResponseDto responseDto : responseDtoList) {
                csvPrinter.printRecord(responseDto.id(), responseDto.name(), responseDto.overtimeMinutes(), "");
            }

            csvPrinter.flush();
        } catch (IOException e) {
            log.error("CSV 파일 생성 중 오류가 발생하였습니다.. {}", e.getMessage());
        }
    }

    private int calculateWorkingDays(YearMonth yearMonth) {
//        String url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey=" + key + "&solYear=" + yearMonth.getYear() + "&solMonth=" + String.format("%02d", yearMonth.getMonthValue());

        try {
            UriComponents uri = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("apis.data.go.kr")
                    .path("/B090041/openapi/service/SpcdeInfoService/getRestDeInfo")
                    .queryParam("serviceKey", URLEncoder.encode(key, "UTF-8"))
                    .queryParam("solYear", URLEncoder.encode(Integer.toString(yearMonth.getYear()), "UTF-8"))
                    .queryParam("solMonth", URLEncoder.encode(String.format("%02d", yearMonth.getMonthValue()), "UTF-8"))
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_JSON.toString());
            headers.set("Accept", MediaType.APPLICATION_JSON.toString());

            HttpEntity<?> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = this.restTemplate.exchange(uri.toString(), HttpMethod.GET, request, String.class);

            System.out.println(uri.toString());
            System.out.println(response.getBody());


            int totalHoliday = this.objectMapper.readTree(response.getBody()).path("response").path("body").path("totalCount").asInt();
            int weekends = calculateWeekends(yearMonth);
            System.out.println(uri);
            System.out.println(yearMonth.lengthOfMonth());
            System.out.println(totalHoliday);
            System.out.println(yearMonth.lengthOfMonth() - totalHoliday - weekends);
            return Math.max(yearMonth.lengthOfMonth() - totalHoliday - weekends, 0);
        } catch (Exception e) {
            log.error("error occurred: {}", e.getMessage());
            return -1;
        }
    }

    private int calculateTotalWorkMinutesForEmployee(Employee employee, YearMonth yearMonth) {
        List<Attendance> attendances = this.attendanceRepository.findByEmployeeIdAndMonthAndYear(employee.getId(), yearMonth.getYear(), yearMonth.getMonthValue());

        long totalWorkMinutes = attendances.stream()
                .filter(a -> a.getClockInTime() != null && a.getClockOutTime() != null)
                .mapToLong(a -> ChronoUnit.MINUTES.between(a.getClockInTime(), a.getClockOutTime()))
                .sum();

        return (int) totalWorkMinutes;
    }

    private int calculateWeekends(YearMonth yearMonth) {
        int weekends = 0;
        LocalDate date = yearMonth.atDay(1);

        while (date.getMonth() == yearMonth.getMonth()) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                weekends++;
            }
            date = date.plusDays(1);
        }

        return weekends;
    }
}
