package study.crispin.team.infrastructure.entity;

import jakarta.persistence.*;
import study.crispin.team.domain.Team;

@Entity
@Table(name = "teams")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String manager;

    protected TeamEntity() {
    }

    public TeamEntity(Long id, String name, String manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
    }

    public static TeamEntity fromModel(Team team) {
        return new TeamEntity(
                team.id(),
                team.name(),
                team.manager()
        );
    }

    public Team toModel() {
        return Team.of(
                this.id,
                this.name,
                this.manager
        );
    }

    public static Team toModel(TeamEntity teamEntity) {
        return Team.of(
                teamEntity.id,
                teamEntity.name,
                teamEntity.manager
        );
    }
}
