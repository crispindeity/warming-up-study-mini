package study.crispin.attendance;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.crispin.attendance.application.AttendanceService;
import study.crispin.attendance.infrastructure.repository.AttendanceRepository;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.common.DateTimeHolder;
import study.crispin.fixture.TestMemberFixture;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.mock.FakeAttendanceRepository;
import study.crispin.mock.FakeDateTimeHolder;
import study.crispin.mock.FakeMemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@DisplayName("출퇴근 서비스 테스트")
class AttendanceServiceTest {

    private DateTimeHolder dateTimeHolder;
    private MemberRepository memberRepository;
    private AttendanceService attendanceService;
    private AttendanceRepository fakeAttendanceRepository;

    @BeforeEach
    void setup() {
        LocalDate date = LocalDate.of(2024, 2, 29);
        LocalTime time = LocalTime.of(9, 0, 0);
        dateTimeHolder = new FakeDateTimeHolder(date, time);
        memberRepository = new FakeMemberRepository();
        fakeAttendanceRepository = new FakeAttendanceRepository();
        attendanceService = new AttendanceService(dateTimeHolder, memberRepository, fakeAttendanceRepository);
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
                memberRepository.save(TestMemberFixture.멤버_생성(
                        "테스트멤버1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                ));

                // when
                ClockInResponse response = attendanceService.clockIn(MemberId);

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.id()).isEqualTo(1L);
                    softAssertions.assertThat(response.clockInDateTime())
                            .isEqualTo(LocalDateTime.of(2024, 2, 29, 9, 0, 0));
                });
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
                memberRepository.save(TestMemberFixture.멤버_생성(
                        "테스트멤버1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                ));
                attendanceService.clockIn(memberId);

                // when
                ClockOutResponse response = attendanceService.clockOut(memberId);

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
        }
    }
}
