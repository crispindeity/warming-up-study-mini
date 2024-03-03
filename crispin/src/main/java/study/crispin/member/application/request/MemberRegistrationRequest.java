package study.crispin.member.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MemberRegistrationRequest(
        @NotBlank(message = "이름은 필수 입력값 입니다.")
        String name,
        @NotBlank(message = "팀이름은 필수 입력값 입니다.")
        String teamName,
        @NotNull(message = "생일은 필수 입력값 입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthday,
        @NotNull(message = "근무 시작일은 필수 입력값 입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
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
