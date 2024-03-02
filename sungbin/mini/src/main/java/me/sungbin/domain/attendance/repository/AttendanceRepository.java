package me.sungbin.domain.attendance.repository;

import me.sungbin.domain.attendance.entity.Attendance;
import me.sungbin.domain.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.repository
 * @fileName : AttendanceRepository
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findTopByEmployeeIdOrderByClockInTimeDesc(Long employeeId);

    Optional<Attendance> findByEmployeeAndClockInTimeBetween(Employee employee, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
