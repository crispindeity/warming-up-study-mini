package inflearn.mini.api;

import static inflearn.mini.api.steps.EmployeeSteps.모든_직원_조회;
import static inflearn.mini.api.steps.TeamSteps.팀_등록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import inflearn.mini.api.steps.EmployeeSteps;
import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.team.dto.request.TeamRegisterRequestDto;

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
        final var 직원_등록_응답 = EmployeeSteps.직원_등록(직원_등록_요청);

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
        final var 직원_등록_응답 = EmployeeSteps.직원_등록(직원_등록_요청);

        // then
        assertThat(직원_등록_응답.statusCode()).isEqualTo(NOT_FOUND.value());
    }

    @Test
    void 모든_직원을_조회한다() {
        // given
        // when
        final var 직원_조회_응답 = 모든_직원_조회();

        // then
        assertThat(직원_조회_응답.statusCode()).isEqualTo(OK.value());
    }
}
