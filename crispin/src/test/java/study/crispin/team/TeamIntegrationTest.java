package study.crispin.team;

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
import study.crispin.steps.TeamSteps;
import study.crispin.team.application.request.TeamRegistrationRequest;

@DisplayName("팀 통합 테스트")
public class TeamIntegrationTest extends ApiTest {

    @Nested
    @DisplayName("팀 등록 테스트")
    class TeamRegistrationTest {

        @Nested
        @DisplayName("팀 등록 성공 테스트")
        class TeamRegistrationSuccessTest {

            @Test
            @DisplayName("정상적인 데이터로 팀 등록 요청 시, 팀 저장 후 200 OK 응답을 반환한다.")
            void 팀_등록_성공_테스트() {
                // given
                TeamRegistrationRequest request = TeamRegistrationRequest.of("테스트1팀");

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .when().post("/api/v1/teams")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.OK.value());
                    softAssertions.assertThat(response.body().jsonPath().getLong("id"))
                            .isEqualTo(1L);
                    softAssertions.assertThat(response.body().jsonPath().getString("name"))
                            .isEqualTo(request.name());
                    softAssertions.assertThat(response.body().jsonPath().getString("manager"))
                            .isNull();
                });
            }
        }

        @Nested
        @DisplayName("팀 등록 실패 테스트")
        class TeamRegistrationFailTest {

            @Test
            @DisplayName("이미 등록된 팀을 중복 등록하면, 400 응답을 반환해야 한다.")
            void 이미_등록된_팀_중복_등록_실패_테스트() {
                // given
                TeamRegistrationRequest request = TeamRegistrationRequest.of("테스트1팀");
                TeamSteps.팀_등록_요청(request);

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .when().post("/api/v1/teams")
                        .then().log().all()
                        .extract();

                // then
                SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(response.statusCode())
                            .isEqualTo(HttpStatus.BAD_REQUEST.value());
                    softAssertions.assertThat(response.jsonPath().getString("message"))
                            .isEqualTo(ExceptionMessage.TEAM_NAME_ALREADY_EXISTS.getMessage());
                });
            }
        }
    }

    @Nested
    @DisplayName("팀 조회 테스트")
    class TeamRetrieveTest {

        @Nested
        @DisplayName("팀 조회 성공 테스트")
        class TeamRetrieveSuccessTest {

            @Test
            @DisplayName("정상적인 팀 조회 요청 시, 등록되어 있는 모든 팀 정보와 200 OK 가 반환되어야 한다.")
            void 팀_조회_성공_테스트() {
                // given
                TeamRegistrationRequest request = TeamRegistrationRequest.of("테스트1팀");
                TeamSteps.팀_등록_요청(request);

                // when
                ExtractableResponse<Response> response = RestAssured.given()
                        .log().all()
                        .when().get("/api/v1/teams")
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
}
