package inflearn.mini.api.steps;

import static io.restassured.RestAssured.given;

import org.springframework.http.MediaType;

import inflearn.mini.annualleave.dto.request.AnnualLeaveRequestDto;
import inflearn.mini.annualleave.dto.request.RemainingAnnualLeaveRequestDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AnnualLeaveSteps {

    private static final String ANNUAL_LEAVE_BASE_URL = "/api/v1/annual-leaves";

    public static ExtractableResponse<Response> 연차_신청(final AnnualLeaveRequestDto 연차_신청_요청) {
        return given().log().all()
                .body(연차_신청_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(ANNUAL_LEAVE_BASE_URL)
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 연차_남은_연차_조회(final RemainingAnnualLeaveRequestDto 남은_연차_조회_요청) {
        return given().log().all()
                .body(남은_연차_조회_요청)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(ANNUAL_LEAVE_BASE_URL)
                .then()
                .log().all()
                .extract();
    }
}
