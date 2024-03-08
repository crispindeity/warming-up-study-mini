package study.crispin.steps;

import io.restassured.RestAssured;
import org.springframework.http.MediaType;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.request.MemberUpdateRequest;

public class MemberSteps {

    public static void 멤버_등록_요청(MemberRegistrationRequest request) {
        RestAssured.given()
                .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/v1/members")
                .then().log().all()
                .extract();
    }

    public static void 멤버_업데이트_요청(MemberUpdateRequest updateRequest) {
        RestAssured.given()
                .log().all().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(updateRequest)
                .when().put("/api/v1/members")
                .then().log().all()
                .extract();
    }
}
