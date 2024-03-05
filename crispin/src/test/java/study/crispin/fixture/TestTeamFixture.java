package study.crispin.fixture;


import study.crispin.team.domain.Team;

public class TestTeamFixture {

    public static Team 팀_생성(String name, String manager) {
        return Team.of(name, manager);
    }
}
