package study.crispin.team;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@DisplayName("팀 서비스 테스트")
class TeamServiceTest {

    private TeamService teamService;
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        teamRepository = new FakeTeamRepository();
        teamService = new TeamServiceImpl(teamRepository);
    }

    @Nested
    @DisplayName("팀 등록 테스트")
    class TeamRegistrationTest {

        @Nested
        @DisplayName("팀 등록 성공 테스트")
        class TeamRegistrationSuccessTest {

            @Test
            @DisplayName("팀 등록에 성공하면, 요청 정보를 갖는 팀이 저장되어야한다.")
            void 팀_등록_성공_테스트() {
                // given
                TeamRegistrationRequest request = new TeamRegistrationRequest("테스트 1팀");

                // when
                TeamRegistrationResponse response = teamService.registration(request);

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response)
                            .as("팀 등록 시 반환되는 값은 Null 이면 안된다.").isNotNull();
                    softAssertions.assertThat(response.name())
                            .as("팀 등록 시 요청된 정보에 맞게 등록되어야 한다.").isEqualTo(request.name());
                    softAssertions.assertThat(response.manager())
                            .as("팀 최초 등록 시 팀 매니저는 없어야한다.").isNull();
                });
            }
        }

        @Nested
        @DisplayName("팀 등록 실패 테스트")
        class TeamRegistrationFailTest {

            @Test
            @DisplayName("팀 등록 시 이미 등록되어 있는 이름을 사용하면 예외가 발생해야한다.")
            void 팀_이름_중복_등록_실패_테스트() {
                // given
                팀_등록_요청(1L, "테스트1팀", "테스트1팀매니저");
                TeamRegistrationRequest request = new TeamRegistrationRequest("테스트1팀");

                // when & then
                Assertions.assertThatThrownBy(() -> teamService.registration(request))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이미 존재하는 팀 이름 입니다.");
            }
        }
    }

    private void 팀_등록_요청(Long id, String name, String manager) {
        Team team = Team.of(id, name, manager);
        teamRepository.save(team);
    }

    private record TeamRegistrationRequest(String name) {
    }

    private record Team(Long id, String name, String manager) {

        public static Team of(String name) {
            return new Team(null, name, null);
        }
        public static Team of(Long id, String name, String manager) {
            return new Team(id, name, manager);
        }
    }

    private interface TeamService {
        TeamRegistrationResponse registration(TeamRegistrationRequest request);
    }

    private class TeamServiceImpl implements TeamService {

        private final TeamRepository repository;

        private TeamServiceImpl(TeamRepository repository) {
            this.repository = repository;
        }

        @Override
        public TeamRegistrationResponse registration(TeamRegistrationRequest request) {
            Assert.notNull(request, "요청값은 필수입니다.");
            Assert.notNull(request.name(), "이름은 필수입니다.");

            verifyDuplicateTeamName(request.name());

            Team team = Team.of(request.name());
            Team savedTeam = repository.save(team);

            return new TeamRegistrationResponse(savedTeam.id(), savedTeam.name(), savedTeam.manager());
        }

        private void verifyDuplicateTeamName(String name) {
            Assert.notNull(name, "이름은 필수입니다.");

            if(teamRepository.findByName(name).isPresent()) {
                throw new IllegalArgumentException("이미 존재하는 팀 이름 입니다.");
            }
        }
    }

    private record TeamRegistrationResponse(Long id, String name, String manager) {
    }

    private interface TeamRepository {
        Team save(Team team);
        Optional<Team> findByName(String name);
    }

    private class FakeTeamRepository implements TeamRepository {

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
}
