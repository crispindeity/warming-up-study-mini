package study.crispin.member.infrastructure.entity;

import jakarta.persistence.*;
import study.crispin.member.domain.Member;
import study.crispin.member.domain.Role;

import java.time.LocalDate;

@Entity
@Table(name = "members")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String teamName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate birthday;
    private LocalDate workStartDate;

    protected MemberEntity() {
    }

    public MemberEntity(Long id, String name, String teamName, Role role, LocalDate birthday, LocalDate workStartDate) {
        this.id = id;
        this.name = name;
        this.teamName = teamName;
        this.role = role;
        this.birthday = birthday;
        this.workStartDate = workStartDate;
    }

    public static MemberEntity fromModel(Member member) {
        return new MemberEntity(
                member.id(),
                member.name(),
                member.teamName(),
                member.role(),
                member.birthday(),
                member.workStartDate()
        );
    }

    public Member toModel() {
        return Member.of(
                this.id,
                this.name,
                this.teamName,
                this.role,
                this.birthday,
                this.workStartDate
        );
    }

    public static Member toModel(MemberEntity memberEntity) {
        return Member.of(
                memberEntity.id,
                memberEntity.name,
                memberEntity.teamName,
                memberEntity.role,
                memberEntity.birthday,
                memberEntity.workStartDate
        );
    }
}
