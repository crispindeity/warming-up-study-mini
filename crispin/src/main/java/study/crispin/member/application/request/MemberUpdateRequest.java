package study.crispin.member.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MemberUpdateRequest(
        @NotBlank(message = "이름은 필수 입력값 입니다.")
        String name,
        @NotNull(message = "생일은 필수 입력값 입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthday,
        @NotNull(message = "근무 시작일은 필수 입력값 입니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate workStartDate
) {

    public static MemberUpdateRequest of(String name, LocalDate birthday, LocalDate workStartDate) {
        return new MemberUpdateRequest(name, birthday, workStartDate);
    }
}
