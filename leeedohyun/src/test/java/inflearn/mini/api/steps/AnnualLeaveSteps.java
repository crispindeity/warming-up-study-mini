package inflearn.mini.api.steps;

import org.springframework.http.MediaType;

import inflearn.mini.annualleave.dto.request.AnnualLeaveRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AnnualLeaveSteps {

    private static final String ANNUAL_LEAVE_BASE_URL = "/api/v1/annual-leaves";

    public static ExtractableResponse<Response> 연차_신청(final AnnualLeaveRequestDto 연차_신청_요청) {
        return RestAssured.given().log().all()
                .body(연차_신청_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(ANNUAL_LEAVE_BASE_URL)
                .then()
                .log().all()
                .extract();
    }
}
