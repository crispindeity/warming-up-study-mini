package me.sungbin.domain.annual.entity;

import jakarta.persistence.*;
import lombok.*;
import me.sungbin.domain.employee.entity.Employee;
import me.sungbin.global.common.entity.BaseDateTimeEntity;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.annual.entity
 * @fileName : AnnualLeave
 * @date : 3/4/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/4/24       rovert         최초 생성
 */

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "createdAt", column = @Column(name = "annual_apply_date", nullable = false, updatable = false))
public class AnnualLeave extends BaseDateTimeEntity {

    @Id
    @Comment("연차 테이블의 PK")
    @Column(name = "annual_leave_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("연차 사용 날짜")
    @Column(nullable = false)
    private LocalDate annualLeaveDate;

    @JoinColumn(name = "employee_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    @Builder
    public AnnualLeave(LocalDate annualLeaveDate, Employee employee) {
        this.annualLeaveDate = annualLeaveDate;
        this.employee = employee;
    }
}
