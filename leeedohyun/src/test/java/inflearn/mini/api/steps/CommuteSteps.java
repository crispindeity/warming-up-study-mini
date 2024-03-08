package inflearn.mini.api.steps;

import static io.restassured.RestAssured.given;

import org.springframework.http.MediaType;

import inflearn.mini.commute.dto.request.CommutingRequestDto;
import inflearn.mini.commute.dto.request.EndOfWorkRequestDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CommuteSteps {

    private static final String BASE_URL = "/api/v1";

    public static ExtractableResponse<Response> 출근(final CommutingRequestDto 출근_요청) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(출근_요청)
                .when()
                .post(BASE_URL + "/work")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 퇴근(final EndOfWorkRequestDto 퇴근_요청) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(퇴근_요청)
                .when()
                .patch(BASE_URL + "/leave")
                .then()
                .log().all()
                .extract();
    }
}
