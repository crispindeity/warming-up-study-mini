package inflearn.mini.api.steps;

import static io.restassured.RestAssured.given;

import org.springframework.http.MediaType;

import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class EmployeeSteps {

    private static final String EMPLOYEE_BASE_URL = "/api/v1/employees";

    public static ExtractableResponse<Response> 직원_등록(final EmployeeRegisterRequestDto 직원_등록_요청) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(직원_등록_요청)
                .when()
                .post(EMPLOYEE_BASE_URL + "/register")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 모든_직원_조회() {
        return given().log().all()
                .when()
                .get(EMPLOYEE_BASE_URL)
                .then()
                .log().all()
                .extract();
    }
}
