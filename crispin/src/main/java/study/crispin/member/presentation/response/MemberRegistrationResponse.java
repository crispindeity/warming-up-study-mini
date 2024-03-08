package study.crispin.member.presentation.response;

import study.crispin.member.domain.Role;

import java.time.LocalDate;

public record MemberRegistrationResponse(
        Long id,
        String name,
        String teamName,
        Role role,
        LocalDate birthday,
        LocalDate workStartDate
) {
    public static MemberRegistrationResponse of(Long id, String name, String teamName, Role role, LocalDate birthday, LocalDate workStartDate) {
        return new MemberRegistrationResponse(id, name, teamName, role, birthday, workStartDate);
    }
}
