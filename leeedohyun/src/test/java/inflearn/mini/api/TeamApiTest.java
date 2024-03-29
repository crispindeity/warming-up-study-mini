package inflearn.mini.api;

import static inflearn.mini.api.steps.TeamSteps.팀_등록;
import static inflearn.mini.api.steps.TeamSteps.팀_조회;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;

import inflearn.mini.team.dto.request.TeamRegisterRequestDto;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class TeamApiTest extends ApiTest {

    @Test
    void 팀을_등록한다() {
        // given
        final TeamRegisterRequestDto 팀_등록_요청 = new TeamRegisterRequestDto("팀");

        // when
        final ExtractableResponse<Response> 팀_등록_응답 = 팀_등록(팀_등록_요청);

        // then
        assertThat(팀_등록_응답.statusCode()).isEqualTo(OK.value());
    }

    @Test
    void 팀_등록_시_이미_등록된_팀이면_실패한다() {
        // given
        팀_등록(new TeamRegisterRequestDto("팀"));
        final TeamRegisterRequestDto 팀_등록_요청 = new TeamRegisterRequestDto("팀");

        // when
        final ExtractableResponse<Response> 팀_등록_응답 = 팀_등록(팀_등록_요청);

        // then
        assertThat(팀_등록_응답.statusCode()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    void 팀을_조회한다() {
        // given
        팀_등록(new TeamRegisterRequestDto("개발팀"));
        팀_등록(new TeamRegisterRequestDto("영업팀"));

        // when
        final ExtractableResponse<Response> 팀_조회_응답 = 팀_조회();

        // then
        assertThat(팀_조회_응답.statusCode()).isEqualTo(OK.value());
    }
}
