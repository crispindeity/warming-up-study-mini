package study.crispin.steps;

import io.restassured.RestAssured;
import org.springframework.http.MediaType;
import study.crispin.team.application.request.TeamRegistrationRequest;

public class TeamSteps {


    public static void 팀_등록_요청(TeamRegistrationRequest request) {
        RestAssured.given()
                .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/v1/teams")
                .then().log().all()
                .extract();
    }
}
