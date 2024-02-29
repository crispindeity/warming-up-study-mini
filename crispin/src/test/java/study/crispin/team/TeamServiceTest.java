package study.crispin.team;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.crispin.fixture.TestTeamFixture;
import study.crispin.mock.FakeTeamRepository;
import study.crispin.team.application.request.TeamRegistrationRequest;
import study.crispin.team.application.service.TeamServiceImpl;
import study.crispin.team.infrastructure.repository.TeamRepository;
import study.crispin.team.presentation.port.TeamService;
import study.crispin.team.presentation.response.TeamRegistrationResponse;

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
                TeamRegistrationRequest request = new TeamRegistrationRequest("테스트1팀");

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
                teamRepository.save(TestTeamFixture.팀_생성(1L, "테스트1팀", "테스트1팀매니저"));
                TeamRegistrationRequest request = new TeamRegistrationRequest("테스트1팀");

                // when & then
                Assertions.assertThatThrownBy(() -> teamService.registration(request))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이미 존재하는 팀 이름 입니다.");
            }
        }
    }

}
