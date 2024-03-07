package study.crispin.member.presentation.response;

import study.crispin.member.domain.Role;

import java.time.LocalDate;

public record MemberRetrieveResponse(
        String name,
        String teamName,
        Role role,
        LocalDate birthday,
        LocalDate workStartDate
) {
    public static MemberRetrieveResponse of(
            String name,
            String teamName,
            Role role,
            LocalDate birthday,
            LocalDate workStartDate
    ) {
        return new MemberRetrieveResponse(name, teamName, role, birthday, workStartDate);
    }
}
