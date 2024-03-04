package inflearn.mini.api;

import static inflearn.mini.api.steps.EmployeeSteps.모든_직원_조회;
import static inflearn.mini.api.steps.EmployeeSteps.직원_등록;
import static inflearn.mini.api.steps.EmployeeSteps.출근;
import static inflearn.mini.api.steps.EmployeeSteps.퇴근;
import static inflearn.mini.api.steps.TeamSteps.팀_등록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

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
        final var 직원_등록_응답 = 직원_등록(직원_등록_요청);

        // then
        assertThat(직원_등록_응답.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 직원을_등록할_때_팀이_존재하지_않으면_실패한다() {
        // given
        final EmployeeRegisterRequestDto 직원_등록_요청 = EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();

        // when
        final var 직원_등록_응답 = 직원_등록(직원_등록_요청);

        // then
        assertThat(직원_등록_응답.statusCode()).isEqualTo(NOT_FOUND.value());
    }

    @Test
    void 모든_직원을_조회한다() {
        // given
        팀_등록(new TeamRegisterRequestDto("개발팀"));
        final EmployeeRegisterRequestDto 직원_등록_요청1 = EmployeeRegisterRequestDto.builder()
                .employeeName("홍길동")
                .teamName("개발팀")
                .isManager(false)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();
        final EmployeeRegisterRequestDto 직원_등록_요청2 = EmployeeRegisterRequestDto.builder()
                .employeeName("김철수")
                .teamName("개발팀")
                .isManager(true)
                .birthday(LocalDate.of(1990, 1, 1))
                .workStartDate(LocalDate.of(2021, 1, 1))
                .build();

        직원_등록(직원_등록_요청1);
        직원_등록(직원_등록_요청2);

        // when
        final var 직원_조회_응답 = 모든_직원_조회();

        // then
        assertThat(직원_조회_응답.statusCode()).isEqualTo(OK.value());
    }

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

        // when
        final ExtractableResponse<Response> 출근_응답 = 출근(1L);

        // then
        assertThat(출근_응답.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 출근_시_등록된_직원이_아니면_실패한다() {
        // given
        final Long 직원_아이디 = 1L;

        // when
        final ExtractableResponse<Response> 출근_응답 = 출근(직원_아이디);

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
        출근(1L);

        // when
        final ExtractableResponse<Response> 퇴근_응답 = 퇴근(1L);

        // then
        assertThat(퇴근_응답.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 퇴근_시_등록되지_않은_직원인_경우_실패한다() {
        // given

        // when
        final ExtractableResponse<Response> 퇴근_응답 = 퇴근(1L);

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

        // when
        final ExtractableResponse<Response> 퇴근_응답 = 퇴근(1L);

        // then
        assertThat(퇴근_응답.statusCode()).isEqualTo(BAD_REQUEST.value());
    }
}
