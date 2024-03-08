package inflearn.mini.api;

import static inflearn.mini.api.steps.EmployeeSteps.직원_등록;
import static inflearn.mini.api.steps.TeamSteps.팀_등록;
import static inflearn.mini.api.steps.CommuteSteps.출근;
import static inflearn.mini.api.steps.CommuteSteps.퇴근;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import inflearn.mini.commute.dto.request.CommutingRequestDto;
import inflearn.mini.commute.dto.request.EndOfWorkRequestDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class CommuteApiTest extends ApiTest {

    @Test
    void 출근한다() {
        // given
        팀_등록(new TeamRegisterRequestDto("개발팀"));
        직원_등록(EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build());
        final CommutingRequestDto 출근_요청 = new CommutingRequestDto(1L);

        // when
        final ExtractableResponse<Response> 출근_응답 = 출근(출근_요청);

        // then
        assertThat(출근_응답.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 출근_시_등록된_직원이_아니면_실패한다() {
        // given
        final CommutingRequestDto 출근_요청 = new CommutingRequestDto(1L);

        // when
        final ExtractableResponse<Response> 출근_응답 = 출근(출근_요청);

        // then
        assertThat(출근_응답.statusCode()).isEqualTo(NOT_FOUND.value());
    }

    @Test
    void 퇴근한다() {
        // given
        팀_등록(new TeamRegisterRequestDto("개발팀"));
        직원_등록(EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build());
        출근(new CommutingRequestDto(1L));

        final EndOfWorkRequestDto 퇴근_요청 = new EndOfWorkRequestDto(1L);

        // when
        final ExtractableResponse<Response> 퇴근_응답 = 퇴근(퇴근_요청);

        // then
        assertThat(퇴근_응답.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 퇴근_시_등록되지_않은_직원인_경우_실패한다() {
        // given
        final EndOfWorkRequestDto 퇴근_요청 = new EndOfWorkRequestDto(1L);

        // when
        final ExtractableResponse<Response> 퇴근_응답 = 퇴근(퇴근_요청);

        // then
        assertThat(퇴근_응답.statusCode()).isEqualTo(NOT_FOUND.value());
    }

    @Test
    void 퇴근_시_출근하지_않은_직원인_경우_실패한다() {
        // given
        팀_등록(new TeamRegisterRequestDto("개발팀"));
        직원_등록(EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build());

        final EndOfWorkRequestDto 퇴근_요청 = new EndOfWorkRequestDto(1L);

        // when
        final ExtractableResponse<Response> 퇴근_응답 = 퇴근(퇴근_요청);

        // then
        assertThat(퇴근_응답.statusCode()).isEqualTo(BAD_REQUEST.value());
    }
}
