package me.sungbin.domain.attendance.entity;

import jakarta.persistence.*;
import lombok.*;
import me.sungbin.domain.employee.entity.Employee;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.entity
 * @fileName : Attendance
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {

    @Id
    @Comment("출퇴근 테이블 PK")
    @Column(name = "attendance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("출근 시간")
    @Column(nullable = false, updatable = false)
    private LocalDateTime clockInTime;

    @Comment("퇴근 시간")
    private LocalDateTime clockOutTime;

    @JoinColumn(name = "employee_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    @Builder
    public Attendance(Employee employee) {
        this.employee = employee;
    }

    /**
     * 출근 시간 기록
     */
    public void clockIn() {
        this.clockInTime = LocalDateTime.now();
    }

    public void clockOut() {
        this.clockOutTime = LocalDateTime.now();
    }
}
