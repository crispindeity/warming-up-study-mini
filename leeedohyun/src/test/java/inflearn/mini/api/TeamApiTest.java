package inflearn.mini.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class TeamApiTest extends ApiTest {

    @Test
    void 팀을_등록한다() {
        // given
        final TeamRegisterRequestDto 팀_등록_요청 = new TeamRegisterRequestDto("팀");

        // when
        final ExtractableResponse<Response> 팀_등록_응답 = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(팀_등록_요청)
                .when()
                .post("/api/v1/teams/register")
                .then()
                .log().all()
                .extract();

        // then
        assertThat(팀_등록_응답.statusCode()).isEqualTo(200);
    }
}
