package study.crispin.team.application.request;

import jakarta.validation.constraints.NotBlank;

public record TeamRegistrationRequest(
        @NotBlank(message = "이름은 필수 입력값 입니다.") String name
) {
    public static TeamRegistrationRequest of(String name) {
        return new TeamRegistrationRequest(name);
    }
}
