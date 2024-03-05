package study.crispin.team.domain;

import study.crispin.member.domain.Member;

public record Team(Long id, String name, String manager) {

    public static Team of(String name) {
        return new Team(null, name, null);
    }

    public static Team of(String name, String manager) {
        return new Team(null, name, manager);
    }

    public static Team of(Long id, String name, String manager) {
        return new Team(id, name, manager);
    }

    public boolean isEqualByName(String name) {
        return this.name.equals(name);
    }

    public Team updateManager(Member member) {
        if (member.isTeamManager()) {
            return Team.of(this.id, this.name, member.name());
        }
        return Team.of(this.id, this.name, null);
    }

    public boolean isRegisteredManager(String memberName) {
        return this.manager != null && !this.manager.equals(memberName);
    }
}
