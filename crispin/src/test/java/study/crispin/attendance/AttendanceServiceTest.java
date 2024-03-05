package study.crispin.attendance;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import study.crispin.fixture.TestMemberFixture;
import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.mock.FakeMemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@DisplayName("출퇴근 서비스 테스트")
class AttendanceServiceTest {

    private DateHolder dateHolder;
    private MemberRepository memberRepository;
    private AttendanceService attendanceService;
    private AttendanceRepository attendanceRepository;

    @BeforeEach
    void setup() {
        LocalDate date = LocalDate.of(2024, 2, 29);
        LocalTime time = LocalTime.of(9, 0, 0);
        dateHolder = new FakeDateHolder(date, time);
        memberRepository = new FakeMemberRepository();
        attendanceRepository = new AttendanceRepository();
        attendanceService = new AttendanceService(dateHolder, memberRepository, attendanceRepository);
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
                    softAssertions.assertThat(response.clockInDateTime())
                            .isEqualTo(LocalDateTime.of(2024, 2, 29, 9, 0, 0));
                    softAssertions.assertThat(response.id()).isEqualTo(1L);
                });
            }
        }
    }

    public class AttendanceService {

        private DateHolder dateHolder;
        private MemberRepository memberRepository;
        private AttendanceRepository attendanceRepository;

        public AttendanceService(DateHolder dateHolder, MemberRepository memberRepository, AttendanceRepository attendanceRepository) {
            this.dateHolder = dateHolder;
            this.memberRepository = memberRepository;
            this.attendanceRepository = attendanceRepository;
        }

        public ClockInResponse clockIn(Long memberId) {
            LocalDateTime clockInDateTime = dateHolder.now();
            Member findedMember = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException(""));
            Attendance attendance = Attendance.clockIn(findedMember, clockInDateTime);
            Attendance savedAttendance = attendanceRepository.save(attendance);
            return ClockInResponse.of(
                    savedAttendance.id(), savedAttendance.member.name(), savedAttendance.clockInDateTime()
            );
        }
    }

    public record Attendance(Long id, Member member, LocalDateTime clockInDateTime, LocalDateTime clockOutDateTime) {

        public Attendance(Member member, LocalDateTime clockInDateTime, LocalDateTime clockOutDateTime) {
            this(null, member, clockInDateTime, clockOutDateTime);
        }

        public static Attendance clockIn(Member member, LocalDateTime clockInDateTime) {
            return new Attendance(member, clockInDateTime, null);
        }

        public static Attendance of(Long id, Member member, LocalDateTime clockInDateTime) {
            return new Attendance(id, member, clockInDateTime, null);
        }
    }

    public interface DateHolder {
        LocalDateTime now();
    }

    public class SystemDateHolder implements DateHolder {
        @Override
        public LocalDateTime now() {
            return LocalDateTime.now();
        }
    }

    public class FakeDateHolder implements DateHolder {

        private final LocalDate date;
        private final LocalTime time;

        public FakeDateHolder(LocalDate date, LocalTime time) {
            this.date = date;
            this.time = time;
        }

        @Override
        public LocalDateTime now() {
            return LocalDateTime.of(this.date, this.time);
        }
    }

    private record ClockInResponse(Long id, String memberName, LocalDateTime clockInDateTime) {
        public static ClockInResponse of(Long id, String name, LocalDateTime clockInDateTime) {
            return new ClockInResponse(id, name, clockInDateTime);
        }
    }

    private class AttendanceRepository {
        private final Map<Long, Attendance> storage = new HashMap<>();
        private Long sequence = 0L;

        public Attendance save(Attendance attendance) {
            if (attendance.id() == null || storage.get(attendance.id()) == null) {
                Attendance newAttendance = Attendance.of(
                        ++sequence,
                        attendance.member(),
                        attendance.clockInDateTime()
                );
                storage.put(sequence, newAttendance);
                return storage.get(sequence);
            } else {
                storage.put(sequence, attendance);
                return storage.get(attendance.id());
            }
        }
    }
}
