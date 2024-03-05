package inflearn.mini.workHub.team.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@RequiredArgsConstructor(access = PROTECTED)
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    @Builder
    public Team(final String name) {
        this.name = name;
    }

}
