package study.crispin.steps;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import study.crispin.attendance.application.request.ClockInOrOutRequest;

public class AttendanceSteps {

    public static void 출근_등록_요청(ClockInOrOutRequest request) {
        RestAssured.given()
                .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/v1/clock-in")
                .then().log().all()
                .extract();
    }

    public static void 퇴근_등록_요청(ClockInOrOutRequest request) {
        RestAssured.given()
                .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/v1/clock-out")
                .then().log().all()
                .extract();
    }
}
