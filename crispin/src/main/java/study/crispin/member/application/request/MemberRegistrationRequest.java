package study.crispin.member.application.request;

import java.time.LocalDate;

public record MemberRegistrationRequest(String name, String teamName, LocalDate birthday, LocalDate workStartDate) {
}
