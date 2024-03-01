package me.sungbin.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import me.sungbin.global.common.entity.BaseDateTimeEntity;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.entity
 * @fileName : Member
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createdAt", column = @Column(name = "work_start_date", nullable = false, updatable = false, columnDefinition = "회사에 들어온 일자")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false, columnDefinition = "직원 정보가 최종 수정된 날짜"))
})
public class Employee extends BaseDateTimeEntity {

    @Id
    @Comment("직원 테이블 PK")
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("직원 이름")
    @Column(name = "employee_name", nullable = false)
    private String name;

    @Comment("팀의 매니저인지 아닌지 여부")
    @Column(nullable = false)
    private boolean isManager;

    @Column(nullable = false)
    private LocalDate birthday;

    @Builder
    public Employee(String name, boolean isManager, LocalDate birthday) {
        this.name = name;
        this.isManager = isManager;
        this.birthday = birthday;
    }
}
