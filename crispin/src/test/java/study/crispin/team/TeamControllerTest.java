package study.crispin.team;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import study.crispin.common.controller.ExceptionControllerAdvice;
import study.crispin.team.application.request.TeamRegistrationRequest;
import study.crispin.team.application.service.TeamService;
import study.crispin.team.presentation.controller.TeamController;
import study.crispin.team.presentation.response.TeamRegistrationResponse;
import study.crispin.team.presentation.response.TeamRetrieveResponse;
import study.crispin.team.presentation.response.TeamRetrieveResponses;

import java.nio.charset.StandardCharsets;
import java.util.List;

@WebMvcTest(TeamController.class)
@DisplayName("팀 컨트롤러 테스트")
public class TeamControllerTest {

    @MockBean
    private TeamService teamService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TeamController(teamService))
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Nested
    @DisplayName("팀 등록 컨트롤러 테스트")
    class TeamRegistrationControllerTest {

        @Nested
        @DisplayName("팀 등록 성공 테스트")
        class TeamRegistrationSuccessTest {

            @Test
            @DisplayName("정상적인 데이터로 팀 등록 요청 시, 요청 처리 후 정상 응답을 반환한다.")
            void 팀_등록_성공_테스트() throws Exception {
                // given
                TeamRegistrationRequest request = TeamRegistrationRequest.of("테스트1팀");
                TeamRegistrationResponse response = TeamRegistrationResponse.of(
                        1L,
                        "테스트1팀",
                        null
                );

                BDDMockito.given(teamService.registration(request)).willReturn(response);

                // when
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/team")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().json(stringify(response)));

                // then
                BDDMockito.then(teamService).should().registration(request);
            }
        }

        @Nested
        @DisplayName("팀 등록 실패 테스트")
        class TeamRegistrationFailTest {

            @Test
            @DisplayName("이름 값이 비어 있는 요청이 들어오면, 예외가 발생해야한다.")
            void 비어있는_이름_요청_팀_등록_실패_테스트() throws Exception {
                // given
                TeamRegistrationRequest request = TeamRegistrationRequest.of("");

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/team")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("이름 값이 null인 있는 요청이 들어오면, 예외가 발생해야한다.")
            void null_이름_요청_팀_등록_실패_테스트() throws Exception {
                // given
                TeamRegistrationRequest request = TeamRegistrationRequest.of(null);

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/team")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("이름 값이 공백인 있는 요청이 들어오면, 예외가 발생해야한다.")
            void 공백_이름_요청_팀_등록_실패_테스트() throws Exception {
                // given
                TeamRegistrationRequest request = TeamRegistrationRequest.of(" ");

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/team")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }
        }
    }

    @Nested
    @DisplayName("팀 조회 컨트롤러 테스트")
    class TeamRetrieveControllerTest {

        @Nested
        @DisplayName("팀 조회 성공 테스트")
        class TeamRetrieveSuccessTest {

            @Test
            @DisplayName("정상적인 팀 조회 요청 시, 요청 처리 후 정상 응답을 반환해야 한다.")
            void 팀_조회_성공_테스트() throws Exception {
                // given
                TeamRetrieveResponse response = TeamRetrieveResponse.of(
                        "테스트1팀",
                        null,
                        3L
                );
                TeamRetrieveResponses responses = TeamRetrieveResponses.of(List.of(response));

                BDDMockito.given(teamService.retrieve()).willReturn(responses);

                // when
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/team")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().json(stringify(responses)));

                // then
                BDDMockito.then(teamService).should().retrieve();
            }
        }
    }

    private String stringify(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}
