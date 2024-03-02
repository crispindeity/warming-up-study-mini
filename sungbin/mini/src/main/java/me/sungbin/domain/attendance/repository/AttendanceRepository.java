package me.sungbin.domain.attendance.repository;

import me.sungbin.domain.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
