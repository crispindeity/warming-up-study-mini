package study.crispin.attendance;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.crispin.attendance.application.AttendanceService;
import study.crispin.attendance.application.request.WorkHoursInquiryRequest;
import study.crispin.attendance.infrastructure.repository.AttendanceRepository;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.attendance.presentation.response.WorkHoursInquiryResponses;
import study.crispin.common.DateTimeHolder;
import study.crispin.common.exception.ExceptionMessage;
import study.crispin.common.exception.NotFoundException;
import study.crispin.common.exception.VerificationException;
import study.crispin.fixture.TestMemberFixture;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.mock.FakeAttendanceRepository;
import study.crispin.mock.FakeDateTimeHolder;
import study.crispin.mock.FakeMemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DisplayName("출퇴근 서비스 테스트")
class AttendanceServiceTest {

    private DateTimeHolder dateTimeHolder;
    private MemberRepository memberRepository;
    private AttendanceService attendanceService;
    private AttendanceRepository fakeAttendanceRepository;

    @BeforeEach
    void setup() {
        memberRepository = new FakeMemberRepository();
        fakeAttendanceRepository = new FakeAttendanceRepository();
        dateTimeHolder = new FakeDateTimeHolder();
        attendanceService = new AttendanceService(memberRepository, fakeAttendanceRepository);
        memberRepository.save(TestMemberFixture.멤버_생성(
                "테스트멤버1",
                "테스트1팀",
                LocalDate.of(1999, 9, 9),
                LocalDate.of(2024, 2, 29)
        ));
    }

    @Nested
    @DisplayName("출근 등록 테스트")
    class ClockInTest {

        @Nested
        @DisplayName("출근 등록 성공 테스트")
        class ClockInSuccessTest {

            @Test
            @DisplayName("출근 등록에 성공하면, 요청 정보를 갖는 출근 데이터가 저장되어야 한다.")
            void 출근_등록_성공_태스트() {
                // given
                Long MemberId = 1L;
                LocalDateTime now = dateTimeHolder.now();

                // when
                ClockInResponse response = attendanceService.clockIn(MemberId, now);

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.id()).isEqualTo(1L);
                    softAssertions.assertThat(response.clockInDateTime())
                            .isEqualTo(LocalDateTime.of(2024, 2, 29, 9, 0, 0));
                });
            }

            @Test
            @DisplayName("츨근 등록 후 다음날이 되면 다시 출근 등록이 가능해야 한다.")
            void 출근_등록_후_다음날_출근_등록_성공_테스트() {
                // given
                Long MemberId = 1L;
                LocalDateTime yesterday = dateTimeHolder.now();
                attendanceService.clockIn(MemberId, yesterday);

                // when
                ClockInResponse response = attendanceService.clockIn(MemberId, yesterday.plusDays(1L));

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.id()).isEqualTo(2L);
                    softAssertions.assertThat(response.clockInDateTime())
                            .isEqualTo(LocalDateTime.of(2024, 3, 1, 9, 0, 0));
                });
            }

            @Test
            @DisplayName("퇴근 후 다시 출근 등록을 하는 경우, 출근 등록에 성공해야 한다.")
            void 퇴근_후_다시_출근_등록_성공_테스트() {
                // given
                Long memberId = 1L;
                LocalDateTime clockInDateTime = dateTimeHolder.now();
                LocalDateTime clockOutDateTime = clockInDateTime.plusHours(1L);

                attendanceService.clockIn(memberId, clockInDateTime);
                attendanceService.clockOut(memberId, clockOutDateTime);

                LocalDateTime reClockInDateTime = clockOutDateTime.plusHours(2L);

                // when & then
                Assertions.assertThatCode(() -> attendanceService.clockIn(memberId, reClockInDateTime))
                        .doesNotThrowAnyException();

            }
        }

        @Nested
        @DisplayName("출근 등록 실패 테스트")
        class ClockInFailTest {

            @Test
            @DisplayName("출근 등록 후, 다시 출근 등록을 하면 예외가 발생해야 한다.")
            void 출근_등록_후_재_출근_등록_실패_테스트() {
                // given
                Long memberId = 1L;
                LocalDateTime clockInDateTime = dateTimeHolder.now();
                LocalDateTime clockOutDateTime = clockInDateTime.plusHours(1L);

                attendanceService.clockIn(memberId, clockInDateTime);

                // when & then
                Assertions.assertThatThrownBy(() -> attendanceService.clockIn(memberId, clockOutDateTime))
                        .isInstanceOf(VerificationException.class)
                        .hasMessage(ExceptionMessage.ALREADY_CLOCKED_IN.getMessage());
            }

            @Test
            @DisplayName("등록되지 않은 직원이 출근 등록을 하는 경우, 예외가 발생해야 한다.")
            void 등록_되지_않은_직원_출근_등록_실패_테스트() {
                // given
                Long unregisteredMemberId = 2L;
                LocalDateTime clockInDateTime = dateTimeHolder.now();

                // when & then
                Assertions.assertThatThrownBy(() -> attendanceService.clockIn(unregisteredMemberId, clockInDateTime))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage(ExceptionMessage.UNREGISTERED_MEMBER.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("퇴근 등룩 테스트")
    class ClockOutTest {

        @Nested
        @DisplayName("퇴근 등록 성공 테스트")
        class ClockOutSuccessTest {

            @Test
            @DisplayName("퇴근 등록에 성공하면, 요청 정보를 갖는 퇴근 데이터가 저장되어야 한다.")
            void 퇴근_등록_성공_테스트() {
                // given
                Long memberId = 1L;
                LocalDateTime clockInDateTime = dateTimeHolder.now();
                attendanceService.clockIn(memberId, clockInDateTime);
                LocalDateTime clockOutDateTime = clockInDateTime.plusHours(9L);

                // when
                ClockOutResponse response = attendanceService.clockOut(memberId, clockOutDateTime);

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.id())
                            .isEqualTo(1L);
                    softAssertions.assertThat(response.memberName())
                            .isEqualTo("테스트멤버1");
                    softAssertions.assertThat(response.clockOutDateTime())
                            .isEqualTo(LocalDateTime.of(2024, 2, 29, 18, 0, 0));
                });
            }

            @Test
            @DisplayName("출근 후 퇴근 날짜가 출근 다음날 이어도 퇴근 등록에 성공해야 한다.")
            void 출근_후_퇴근_다음날_등록_성공_테스트() {
                // given
                Long memberId = 1L;
                LocalDateTime clockInDateTime = dateTimeHolder.now();
                attendanceService.clockIn(memberId, clockInDateTime);
                LocalDateTime clockOutDateTime = clockInDateTime.plusHours(20L);

                // when
                ClockOutResponse response = attendanceService.clockOut(memberId, clockOutDateTime);

                // then
                Assertions.assertThat(response.clockOutDateTime())
                        .isEqualTo(LocalDateTime.of(2024, 3, 1, 5, 0, 0));
            }

            @Test
            @DisplayName("퇴근 등록 후 재 퇴근 등록 시, 등록에 성공해야 한다.")
            void 퇴근_등록_후_재_퇴근_등록_성공_테스트() {
                // given
                Long memberId = 1L;
                LocalDateTime clockInDateTime = dateTimeHolder.now();
                LocalDateTime clockOutDateTime = dateTimeHolder.now().plusHours(1L);

                attendanceService.clockIn(memberId, clockInDateTime);
                attendanceService.clockOut(memberId, clockOutDateTime);

                LocalDateTime reClockOutDateTime = clockOutDateTime.plusHours(1L);

                // when & then
                Assertions.assertThatThrownBy(() -> attendanceService.clockOut(memberId, reClockOutDateTime))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage(ExceptionMessage.NOT_CLOCKED_IN.getMessage());
            }
        }

        @Nested
        @DisplayName("퇴근 등록 실패 테스트")
        class ClockOutFailTest {

            @Test
            @DisplayName("출근 등록을 하지 않은 직원이 퇴근 등록을 하는 경우, 예외가 발생해야 한다.")
            void 출근_등록을_하지_않은_직원_퇴근_등록_실패_테스트() {
                // given
                Long memberId = 1L;
                LocalDateTime clockOutDateTime = dateTimeHolder.now();

                // when & then
                Assertions.assertThatThrownBy(() -> attendanceService.clockOut(memberId, clockOutDateTime))
                        .isInstanceOf(NotFoundException.class)
                        .hasMessage(ExceptionMessage.NOT_CLOCKED_IN.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("근무 시간 조회 테스트")
    class WorkHoursInquiryTest {

        @Nested
        @DisplayName("근무 시간 조회 성공 테스트")
        class WorkHoursInquirySuccessTest {

            @Test
            @DisplayName("정상적인 근무 시간 조회 요청 시, 해당 멤버의 근무 시간 내역을 반환해야 한다.")
            void 근무_시간_조회_성공_테스트() {
                // given
                Long memberId = 1L;

                WorkHoursInquiryRequest request = WorkHoursInquiryRequest.of(
                        memberId, LocalDate.of(2024, 3, 1)
                );

                attendanceService.clockIn(memberId, dateTimeHolder.now().plusDays(1L));
                attendanceService.clockOut(memberId, dateTimeHolder.now().plusDays(1L).plusHours(9L));

                // when
                WorkHoursInquiryResponses responses = attendanceService.workHoursInquiry(request);

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(responses.workHoursInquiryResponses()).hasSize(1);
                    softAssertions.assertThat(responses.sum()).isEqualTo(540);
                });
            }

            @Test
            @DisplayName("근무 시간 조회 시, 등록된 근무 시간이 없으면, 비어있는 근무 시간 내역을 반환해야 한다.")
            void 등록된_근무_시간_없이_조회_성공_테스트() {
                // given
                Long memberId = 1L;

                WorkHoursInquiryRequest request = WorkHoursInquiryRequest.of(
                        memberId, LocalDate.of(2024, 3, 1)
                );

                // when
                WorkHoursInquiryResponses responses = attendanceService.workHoursInquiry(request);

                // then
                Assertions.assertThat(responses.workHoursInquiryResponses()).isEmpty();
            }
        }
    }
}
