package study.crispin.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import study.crispin.ApiTest;
import study.crispin.common.exception.ExceptionMessage;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.request.MemberUpdateRequest;
import study.crispin.member.domain.Role;
import study.crispin.steps.MemberSteps;
import study.crispin.steps.TeamSteps;
import study.crispin.team.application.request.TeamRegistrationRequest;

import java.time.LocalDate;

@DisplayName("멤버 통합 테스트")
public class MemberIntegrationTest extends ApiTest {

    @Nested
    @DisplayName("멤버 등록 테스트")
    class MemberRegistrationTest {

        @Nested
        @DisplayName("멤버 등록 성공 테스트")
        class MemberRegistrationSuccessTest {

            @Test
            @DisplayName("정상적인 데이터로 멤버 등록 요청 시, 멤버 저장 후 200 OK 응답을 반환한다.")
            void 멤버_등록_성공_테스트() {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        null,
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .when().post("/api/v1/member")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.OK.value());
                    softAssertions.assertThat(response.body().jsonPath().getString("name"))
                            .isEqualTo(request.name());
                    softAssertions.assertThat(response.body().jsonPath().getString("teamName"))
                            .isNull();
                    softAssertions.assertThat(response.body().jsonPath().getString("birthday"))
                            .isEqualTo("1999-09-09");
                    softAssertions.assertThat(response.body().jsonPath().getString("workStartDate"))
                            .isEqualTo("2024-02-29");
                    softAssertions.assertThat(response.body().jsonPath().getLong("id"))
                            .isEqualTo(1L);
                });
            }
        }

        @Nested
        @DisplayName("멤버 등록 실패 테스트")
        class MemberRegistrationFailTest {

            @Test
            @DisplayName("이미 등록된 멤버를 중복 등록하면, 400 응답을 반환해야 한다.")
            void 이미_등록된_멤버_중복_등록_실패_테스트() {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        null,
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                MemberSteps.멤버_등록_요청(request);

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .when().post("/api/v1/member")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.BAD_REQUEST.value());
                    softAssertions.assertThat(response.body().jsonPath().getString("message"))
                            .isEqualTo(ExceptionMessage.MEMBER_ALREADY_EXISTS.getMessage());
                });
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
            @DisplayName("정상적인 멤버 조회 요청 시, 등록되어 있는 모든 멤버 정보와 200 OK 가 반환되어야 한다.")
            void 멤버_조회_성공_테스트() {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        null,
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                MemberSteps.멤버_등록_요청(request);

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all()
                        .when().get("/api/v1/member")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.OK.value());
                    softAssertions.assertThat(response.jsonPath().getList("result"))
                            .hasSize(1);
                });
            }
        }
    }

    @Nested
    @DisplayName("멤버 수정 테스트")
    class MemberUpdateTest {

        @Nested
        @DisplayName("멤버 수정 성공 테스트")
        class MemberUpdateSuccessTest {

            @Test
            @DisplayName("정상적인 데이터로 멤버 수정 요청 시, 멤버 수정 후 200 OK 응답을 반환한다.")
            void 멤버_수정_성공_테스트() {
                // given
                MemberRegistrationRequest memberRegistrationRequest = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                TeamRegistrationRequest teamRegistrationRequest
                        = TeamRegistrationRequest.of("테스트1팀");

                TeamSteps.팀_등록_요청(teamRegistrationRequest);
                MemberSteps.멤버_등록_요청(memberRegistrationRequest);

                MemberUpdateRequest updateRequest = MemberUpdateRequest.of(
                        "테스트팀원1",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(updateRequest)
                        .when().put("/api/v1/member")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.OK.value());
                    softAssertions.assertThat(response.jsonPath().getString("role"))
                            .isEqualTo(Role.MANAGER.toString());
                });
            }
        }

        @Nested
        @DisplayName("멤버 수정 실패 테스트")
        class MemberUpdateFailTest {

            @Test
            @DisplayName("이미 매니저가 등록되어 있는 경우, 매니저 등록 요청 시 400 응답을 반환해야 한다.")
            void 중복_매니저_등록_실패_테스트() {
                // given
                MemberRegistrationRequest memberRegistrationRequest1 = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );
                MemberRegistrationRequest memberRegistrationRequest2 = MemberRegistrationRequest.of(
                        "테스트팀원2",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );
                TeamRegistrationRequest teamRegistrationRequest
                        = TeamRegistrationRequest.of("테스트1팀");
                MemberUpdateRequest updateRequest1 = MemberUpdateRequest.of(
                        "테스트팀원1",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );
                MemberUpdateRequest updateRequest2 = MemberUpdateRequest.of(
                        "테스트팀원2",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                TeamSteps.팀_등록_요청(teamRegistrationRequest);
                MemberSteps.멤버_등록_요청(memberRegistrationRequest1);
                MemberSteps.멤버_등록_요청(memberRegistrationRequest2);
                MemberSteps.멤버_업데이트_요청(updateRequest1);

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(updateRequest2)
                        .when().put("/api/v1/member")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.BAD_REQUEST.value());
                    softAssertions.assertThat(response.body().jsonPath().getString("message"))
                            .isEqualTo(ExceptionMessage.ALREADY_MANAGER_REGISTERED.getMessage());
                });
            }

            @Test
            @DisplayName("수정하려는 멤버가 팀에 등록되어 있지 않다면, 매니저 등록 요청 시 400 응답을 반환해야 한다.")
            void 팀에_등록_되지_않은_멤버_매니저_등록_실패_테스트() {
                // given
                MemberRegistrationRequest memberRegistrationRequest = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        null,
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );
                MemberSteps.멤버_등록_요청(memberRegistrationRequest);
                MemberUpdateRequest updateRequest = MemberUpdateRequest.of(
                        "테스트팀원1",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(updateRequest)
                        .when().put("/api/v1/member")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.BAD_REQUEST.value());
                    softAssertions.assertThat(response.jsonPath().getString("message"))
                            .isEqualTo(ExceptionMessage.NOT_MEMBER_OF_TEAM.getMessage());
                });
            }
        }
    }
}
