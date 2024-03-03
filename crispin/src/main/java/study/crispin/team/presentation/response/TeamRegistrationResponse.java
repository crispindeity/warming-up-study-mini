package study.crispin.team.presentation.response;

public record TeamRegistrationResponse(Long id, String name, String manager) {
    public static TeamRegistrationResponse of(Long id, String name, String manager) {
        return new TeamRegistrationResponse(id, name, manager);
    }
}
