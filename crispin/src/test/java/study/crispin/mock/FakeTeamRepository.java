package study.crispin.mock;

import study.crispin.team.domain.Team;
import study.crispin.team.infrastructure.repository.TeamRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeTeamRepository implements TeamRepository {

    private final Map<Long, Team> storage = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Team save(Team team) {
        if (team.id() == null || storage.get(team.id()) == null) {
            Team newTeam = Team.of(
                    ++sequence,
                    team.name(),
                    team.manager()
            );
            storage.put(sequence, newTeam);
            return storage.get(sequence);
        } else {
            storage.put(team.id(), team);
            return storage.get(team.id());
        }
    }

    @Override
    public Optional<Team> findByName(String name) {
        return storage.values()
                .stream()
                .filter(team -> team.name().equals(name))
                .findFirst();
    }
}
