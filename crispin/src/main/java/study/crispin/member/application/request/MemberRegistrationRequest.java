package study.crispin.member.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MemberRegistrationRequest(
        @NotBlank(message = "이름은 필수 입력값 입니다.")
        String name,
        String teamName,
        @NotNull(message = "생일은 필수 입력값 입니다.")
        LocalDate birthday,
        @NotNull(message = "근무 시작일은 필수 입력값 입니다.")
        LocalDate workStartDate
) {
    public static MemberRegistrationRequest of(
            String name,
            String teamName,
            LocalDate birthday,
            LocalDate workStartDate
    ) {
        return new MemberRegistrationRequest(name, teamName, birthday, workStartDate);
    }
}
