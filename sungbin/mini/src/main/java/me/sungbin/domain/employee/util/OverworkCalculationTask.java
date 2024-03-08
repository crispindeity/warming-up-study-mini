package me.sungbin.domain.employee.util;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.employee.model.response.OvertimeResponseDto;
import me.sungbin.domain.employee.service.EmployeeService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.List;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.employee.util
 * @fileName : OverworkCalculationTask
 * @date : 3/6/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/6/24       rovert         최초 생성
 */

@Component
@EnableScheduling
@RequiredArgsConstructor
public class OverworkCalculationTask {

    private final EmployeeService employeeService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateOverworkReport() {
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        List<OvertimeResponseDto> overtimeRecords = this.employeeService.calculateOvertimeHours(previousMonth);
        this.employeeService.exportOverTimeRecordsToCSV(overtimeRecords, "overtime_" + previousMonth + ".csv");
    }
}
