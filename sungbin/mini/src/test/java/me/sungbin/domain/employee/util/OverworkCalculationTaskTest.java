package me.sungbin.domain.employee.util;

import me.sungbin.domain.employee.model.response.OvertimeResponseDto;
import me.sungbin.domain.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.employee.util
 * @fileName : OverworkCalculationTaskTest
 * @date : 3/6/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/6/24       rovert         최초 생성
 */
class OverworkCalculationTaskTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private OverworkCalculationTask overworkCalculationTask;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("csv 생성 테스트")
    public void testGenerateOverworkReport() {
        // Arrange
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        List<OvertimeResponseDto> expectedRecords = List.of(/* Mock data */);
        when(employeeService.calculateOvertimeHours(previousMonth)).thenReturn(expectedRecords);

        // Act
        overworkCalculationTask.generateOverworkReport();

        // Assert
        verify(employeeService).calculateOvertimeHours(previousMonth);
        verify(employeeService).exportOverTimeRecordsToCSV(expectedRecords, "overtime_" + previousMonth + ".csv");
    }
}
