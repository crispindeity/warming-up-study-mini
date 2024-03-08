package study.crispin.attendance.infrastructure.entity;

import jakarta.persistence.*;
import study.crispin.attendance.domain.Attendance;
import study.crispin.member.infrastructure.entity.MemberEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendances")
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
    private LocalDateTime clockInDateTime;
    private LocalDateTime clockOutDateTime;

    protected AttendanceEntity() {
    }

    private AttendanceEntity(Long id, MemberEntity member, LocalDateTime clockInDateTime, LocalDateTime clockOutDateTime) {
        this.id = id;
        this.member = member;
        this.clockInDateTime = clockInDateTime;
        this.clockOutDateTime = clockOutDateTime;
    }

    public static AttendanceEntity fromModel(Attendance attendance) {
        return new AttendanceEntity(
                attendance.id(),
                MemberEntity.fromModel(attendance.member()),
                attendance.clockInDateTime(),
                attendance.clockOutDateTime()
        );
    }

    public Attendance toModel() {
        return Attendance.of(
                this.id,
                this.member.toModel(),
                this.clockInDateTime,
                this.clockOutDateTime
        );
    }
}

