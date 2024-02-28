package inflearn.mini.api;

import static inflearn.mini.api.steps.TeamSteps.팀_등록;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class EmployeeApiTest extends ApiTest {

    @Test
    void 직원을_등록한다() {
        // given
        팀_등록(new TeamRegisterRequestDto("개발팀"));
        final EmployeeRegisterRequestDto 직원_등록_요청 = EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();

        // when
        final ExtractableResponse<Response> 직원_등록_응답 = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(직원_등록_요청)
                .when()
                .post("/api/v1/employees/register")
                .then().log().all()
                .extract();

        // then
        assertThat(직원_등록_응답.statusCode()).isEqualTo(200);
    }
}
