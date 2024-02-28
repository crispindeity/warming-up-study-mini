package inflearn.mini.api.steps;

import org.springframework.http.MediaType;

import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class TeamSteps {

    private final static String TEAM_BASE_API_URL = "/api/v1/teams";

    public static ExtractableResponse<Response> 팀_등록(final TeamRegisterRequestDto 팀_등록_요청) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(팀_등록_요청)
                .when()
                .post(TEAM_BASE_API_URL + "/register")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 팀_조회() {
        return RestAssured.given().log().all()
                .when()
                .get(TEAM_BASE_API_URL)
                .then()
                .log().all()
                .extract();
    }
}
