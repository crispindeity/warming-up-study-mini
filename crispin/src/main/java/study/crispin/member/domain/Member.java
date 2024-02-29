package study.crispin.member.domain;

import java.time.LocalDate;

public record Member(Long id, String name, String teamName, Role role, LocalDate birthday,
                     LocalDate workStartDate) {
    public static Member of(String name, String teamName, LocalDate birthday, LocalDate localDate) {
        return new Member(null, name, teamName, Role.MEMBER, birthday, localDate);
    }

    public static Member of(Long id, String name, String teamName, LocalDate birthday, LocalDate localDate) {
        return new Member(id, name, teamName, Role.MEMBER, birthday, localDate);
    }
}