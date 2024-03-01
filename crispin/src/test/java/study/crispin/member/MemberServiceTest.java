package study.crispin.member;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.crispin.fixture.TestMemberFixture;
import study.crispin.fixture.TestTeamFixture;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.request.MemberUpdateRequest;
import study.crispin.member.application.service.MemberServiceImpl;
import study.crispin.member.domain.Role;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.member.application.service.MemberService;
import study.crispin.member.presentation.response.MemberRegistrationResponse;
import study.crispin.member.presentation.response.MemberRetrieveResponses;
import study.crispin.member.presentation.response.MemberUpdateResponse;
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
        teamRepository.save(TestTeamFixture.팀_생성("테스트1팀", null));
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
                memberRepository.save(TestMemberFixture.멤버_생성(
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

    @Nested
    @DisplayName("멤버 수정 테스트")
    class MemberUpdateTest {

        @Nested
        @DisplayName("멤버 룰 수정 테스트")
        class MemberRoleUpdateTest {

            @Nested
            @DisplayName("멤버 룰 수정 성공 테스트")
            class MemberRoleUpdateSuccessTest {

                @Test
                @DisplayName("멤머의 룰을 수정하면, 수정된 룰로 변경되어야 한다.")
                void 멤버_룰_수정_성공_테스트() {
                    // given
                    memberRepository.save(TestMemberFixture.멤버_생성(
                            "테스트멤버1",
                            "테스트1팀",
                            LocalDate.of(1999, 9, 9),
                            LocalDate.of(2024, 2, 29)
                    ));
                    MemberUpdateRequest request = MemberUpdateRequest.of(
                            "테스트멤버1",
                            LocalDate.of(1999, 9, 9),
                            LocalDate.of(2024, 2, 29)
                    );

                    // when
                    MemberUpdateResponse response = memberService.updateRole(request);

                    // then
                    Assertions.assertThat(response.role()).isEqualTo(Role.MANAGER);
                }
            }

            @Nested
            @DisplayName("멤버 룰 수정 실패 테스트")
            class MemberRoleUpdateFailTest {

                @Test
                @DisplayName("팀에 소속되지 않은 멤버의 룰을 수정하면 예외가 발생해야 한다.")
                void 멤버_룰_수정_실패_테스트() {
                    // given
                    memberRepository.save(TestMemberFixture.멤버_생성(
                            "테스트멤버1",
                            null,
                            LocalDate.of(1999, 9, 9),
                            LocalDate.of(2024, 2, 29)
                    ));
                    MemberUpdateRequest request = MemberUpdateRequest.of(
                            "테스트멤버1",
                            LocalDate.of(1999, 9, 9),
                            LocalDate.of(2024, 2, 29)
                    );

                    // when & then
                    Assertions.assertThatThrownBy(() -> memberService.updateRole(request))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("팀에 소속된 멤버가 아닙니다.");
                }
            }
        }
    }

    @Nested
    @DisplayName("멤버 조회 테스트")
    class MemberRetrieveTest {

        @Nested
        @DisplayName("멤버 조회 성공 테스트")
        class MemberRetrieveSuccessTest {

            @Test
            @DisplayName("멤버 조회 시, 등록된 모든 멤버의 정보가 반환되어야 한다.")
            void 멤버_조회_성공_테스트() {
                // given
                memberRepository.save(TestMemberFixture.멤버_생성(
                        "테스트멤버1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                ));
                memberRepository.save(TestMemberFixture.멤버_생성(
                        "테스트멤버1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                ));

                // when
                MemberRetrieveResponses responses = memberService.retrieve();

                // then
                Assertions.assertThat(responses.responses()).hasSize(2);
            }
        }
    }
}
