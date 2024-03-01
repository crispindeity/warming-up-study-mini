package study.crispin.team.presentation.response;

public record TeamRetrieveResponse(String name, String manager, Long memberCount) {
    public static TeamRetrieveResponse of(String name, String manager, Long memberCount) {
        return new TeamRetrieveResponse(name, manager, memberCount);
    }
}
