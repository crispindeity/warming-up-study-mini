package study.crispin.attendance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import study.crispin.ApiTest;
import study.crispin.attendance.application.request.ClockInOrOutRequest;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.steps.AttendanceSteps;
import study.crispin.steps.MemberSteps;

import java.time.LocalDate;

@DisplayName("출,퇴근 통합 테스트")
public class AttendanceIntegrationTest extends ApiTest {

    @Nested
    @DisplayName("출근 등록 테스트")
    class ClockInTest {

        @Nested
        @DisplayName("출근 등록 성공 테스트")
        class ClockInSuccessTest {

            @Test
            @DisplayName("정상적인 데이터로 출근 등록 요청 시, 출근 등록 후 200 OK 응답을 반환해야 한다.")
            void 출근_등록_성공_테스트() {
                // given
                MemberSteps.멤버_등록_요청(
                        MemberRegistrationRequest.of(
                                "테스트팀원1",
                                null,
                                LocalDate.of(1999, 9, 9),
                                LocalDate.of(2024, 2, 29))
                );
                ClockInOrOutRequest request = ClockInOrOutRequest.of(1L);

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .when().post("/api/v1/clock-in")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.body().jsonPath().getLong("id"))
                            .isEqualTo(1L);
                    softAssertions.assertThat(response.body().jsonPath().getString("memberName"))
                            .isEqualTo("테스트팀원1");
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.OK.value());
                });
            }
        }
    }

    @Nested
    @DisplayName("퇴근 등록 테스트")
    class ClockOutTest {

        @Nested
        @DisplayName("퇴근 등록 성공 테스트")
        class ClockOutSuccessTest {

            @Test
            @DisplayName("정상적인 데이터로 퇴근 등록 요청 시, 퇴근 등록 후 200 OK 응답을 반환해야 한다.")
            void 퇴근_등록_성공_테스트() {
                // given
                MemberSteps.멤버_등록_요청(
                        MemberRegistrationRequest.of(
                                "테스트팀원1",
                                null,
                                LocalDate.of(1999, 9, 9),
                                LocalDate.of(2024, 2, 29))
                );
                ClockInOrOutRequest request = ClockInOrOutRequest.of(1L);
                AttendanceSteps.출근_등록_요청(request);

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .when().post("/api/v1/clock-out")
                        .then().log().all()
                        .extract();


                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.body().jsonPath().getLong("id"))
                            .isEqualTo(1L);
                    softAssertions.assertThat(response.body().jsonPath().getString("memberName"))
                            .isEqualTo("테스트팀원1");
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.OK.value());
                });
            }
        }
    }

    @Nested
    @DisplayName("근무 조회 테스트")
    class WorkHoursInquiryTest {

        @Nested
        @DisplayName("근무 조회 성공 테스트")
        class WorkHoursInquirySuccessTest {

            @Test
            @DisplayName("정상적인 데이터로 근무 조회 요청 시, 근무 조회 후 200 OK 응답을 반환해야 한다.")
            void 근무_조회_성공_테스트() {
                // given
                MemberSteps.멤버_등록_요청(
                        MemberRegistrationRequest.of(
                                "테스트팀원1",
                                null,
                                LocalDate.of(1999, 9, 9),
                                LocalDate.of(2024, 2, 29))
                );
                ClockInOrOutRequest clockInOrOutRequest = ClockInOrOutRequest.of(1L);

                AttendanceSteps.출근_등록_요청(clockInOrOutRequest);
                AttendanceSteps.퇴근_등록_요청(clockInOrOutRequest);

                String now = LocalDate.now().toString().substring(0, 7);

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("member-id", "1")
                        .param("date", now)
                        .when().get("/api/v1/work-hours")
                        .then().log().all()
                        .extract();

                // then
                Assertions.assertThat(response.body().jsonPath().getList("detail")).hasSize(1);
            }
        }
    }
}
