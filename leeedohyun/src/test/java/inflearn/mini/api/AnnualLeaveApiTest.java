package inflearn.mini.api;

import static inflearn.mini.api.steps.AnnualLeaveSteps.연차_신청;
import static inflearn.mini.api.steps.EmployeeSteps.직원_등록;
import static inflearn.mini.api.steps.TeamSteps.팀_등록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import inflearn.mini.annualleave.dto.request.AnnualLeaveRequestDto;
import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AnnualLeaveApiTest extends ApiTest {

    @Test
    void 연차를_신청한다() {
        // given
        팀_등록(new TeamRegisterRequestDto("개발팀"));
        직원_등록(EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build());
        final AnnualLeaveRequestDto 연차_신청_요청 = new AnnualLeaveRequestDto(1L, LocalDate.now().plusDays(4));

        // when
        final ExtractableResponse<Response> 연차_신청_응답 = 연차_신청(연차_신청_요청);

        // then
        assertThat(연차_신청_응답.statusCode()).isEqualTo(OK.value());
    }
}
