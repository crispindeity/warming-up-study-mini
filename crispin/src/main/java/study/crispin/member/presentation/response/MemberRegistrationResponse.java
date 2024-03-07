package study.crispin.member.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import study.crispin.member.domain.Role;

import java.time.LocalDate;

public record MemberRegistrationResponse(
        Long id,
        String name,
        String teamName,
        Role role,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthday,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate workStartDate
) {
    public static MemberRegistrationResponse of(Long id, String name, String teamName, Role role, LocalDate birthday, LocalDate workStartDate) {
        return new MemberRegistrationResponse(id, name, teamName, role, birthday, workStartDate);
    }
}
