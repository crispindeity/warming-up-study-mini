package study.crispin.team.domain;

public record Team(Long id, String name, String manager) {

    public static Team of(String name) {
        return new Team(null, name, null);
    }

    public static Team of(Long id, String name, String manager) {
        return new Team(id, name, manager);
    }
}
