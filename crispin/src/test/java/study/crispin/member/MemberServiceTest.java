package study.crispin.member;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.crispin.fixture.TestTeamFixture;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.service.MemberServiceImpl;
import study.crispin.member.domain.Member;
import study.crispin.member.domain.Role;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.member.presentation.port.MemberService;
import study.crispin.member.presentation.response.MemberRegistrationResponse;
import study.crispin.mock.FakeMemberRepository;
import study.crispin.mock.FakeTeamRepository;

import java.time.LocalDate;

@DisplayName("멤버 서비스 테스트")
class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;


    @BeforeEach
    void setup() {
        FakeTeamRepository teamRepository = new FakeTeamRepository();
        memberRepository = new FakeMemberRepository();
        memberService = new MemberServiceImpl(teamRepository, memberRepository);
        teamRepository.save(TestTeamFixture.팀_생성_요청(1L, "테스트1팀", null));
    }

    @Nested
    @DisplayName("멤버 등록 테스트")
    class MemberRegistrationTest {

        @Nested
        @DisplayName("멤버 등록 성공 테스트")
        class MemberRegistrationSuccessTest {

            @Test
            @DisplayName("멤버 등록에 성공하면, 요청 정보를 갖는 멤버가 저장되어야한다.")
            void 멤버_등록_성공_테스트() {
                // given
                MemberRegistrationRequest request = new MemberRegistrationRequest(
                        "테스트멤버1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 28)
                );

                // when
                MemberRegistrationResponse response = memberService.registration(request);

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response)
                            .as("멤버 등록 시 반환되는 값은 Null 이면 안된다.").isNotNull();
                    softAssertions.assertThat(response.name())
                            .as("멤버 등록 시 요청된 정보에 맞게 등록되어야 한다.").isEqualTo(request.name());
                    softAssertions.assertThat(response.role())
                            .as("멤버를 최초 등록 시 Role 은 Member 여야 한다.").isEqualTo(Role.MEMBER);
                });
            }
        }

        @Nested
        @DisplayName("멤버 등록 실패 테스트")
        class MemberRegistrationFailTest {

            @Test
            @DisplayName("멤버 등록 요청 시, 잘못된 팀 이름으로 요청하면 예외가 발생해야한다.")
            void 멤버_등록_실패_잘못된_팀_이름_테스트() {
                // given
                MemberRegistrationRequest request = new MemberRegistrationRequest(
                        "테스트멤버1",
                        "등록되지않은팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 28)
                );

                // when & then
                Assertions.assertThatThrownBy(() -> memberService.registration(request))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("존재하지 않는 팀 이름입니다.");
            }

            @Test
            @DisplayName("멤버 등록 요청 시, 이미 등록되어 있는 멤버일 경우 예외가 발생해야한다.")
            void 멤버_등록_실패_이미_등록된_멤버() {
                // given
                memberRepository.save(TestMemberFixture.멤버_생성_요청(
                        1L,
                        "테스트멤버1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 28)
                ));

                MemberRegistrationRequest request = new MemberRegistrationRequest(
                        "테스트멤버1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 28)
                );

                // when & then
                Assertions.assertThatThrownBy(() -> memberService.registration(request))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이미 존재하는 멤버입니다.");
            }
        }
    }

    public class TestMemberFixture {
        public static Member 멤버_생성_요청(Long id, String name, String teamName, LocalDate birthday, LocalDate workStartDate) {
            return Member.of(id, name, teamName, birthday, workStartDate);
        }
    }
}
