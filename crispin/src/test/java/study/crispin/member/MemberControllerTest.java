package study.crispin.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import study.crispin.common.exception.ExceptionControllerAdvice;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.service.MemberService;
import study.crispin.member.domain.Role;
import study.crispin.member.presentation.controller.MemberController;
import study.crispin.member.presentation.response.MemberRegistrationResponse;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@WebMvcTest(MemberController.class)
@DisplayName("멤버 컨트롤러 테스트")
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public MemberControllerTest() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService))
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Nested
    @DisplayName("멤버 등록 컨트롤러 테스트")
    class MemberRegistrationControllerTest {

        @Nested
        @DisplayName("멤버 등록 성공 테스트")
        class MemberRegistrationSuccessTest {

            @Test
            @DisplayName("정상적인 데이터로 멤버 등록 요청 시, 요청 처리 후 정상 응답을 반환한다.")
            void 멤버_등록_성공_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );
                MemberRegistrationResponse response = MemberRegistrationResponse.of(
                        1L,
                        "테스트팀원1",
                        "테스트1팀",
                        Role.MEMBER,
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                BDDMockito.given(memberService.registration(request)).willReturn(response);

                // when
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().json(stringify(response)));

                // when
                BDDMockito.then(memberService).should().registration(request);
            }
        }

        @Nested
        @DisplayName("멤버 등록 실패 테스트")
        class MemberRegistrationFailTest {

            @Test
            @DisplayName("이름 값이 비어 있는 요청이 들어오면, 예외가 발생해야한다.")
            void 비어있는_이름_요청_멤버_등록_실패_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("이름 값이 null인 요청이 들어오면, 예외가 발생해야한다.")
            void null_이름_요청_멤버_등록_실패_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        null,
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("이름 값이 공백인 요청이 들어오면, 예외가 발생해야한다.")
            void 공백_이름_요청_멤버_등록_실패_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        " ",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("팀이름 값이 비어 있는 요청이 들어오면, 예외가 발생해야한다.")
            void 비어있는_팀이름_요청_멤버_등록_실패_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        "",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("팀이름 값이 null인 요청이 들어오면, 예외가 발생해야한다.")
            void null_팀이름_요청_멤버_등록_실패_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        null,
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("팀이름 값이 공벡인 요청이 들어오면, 예외가 발생해야한다.")
            void 공백_팀이름_요청_멤버_등록_실패_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        " ",
                        LocalDate.of(1999, 9, 9),
                        LocalDate.of(2024, 2, 29)
                );

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("생일 값이 null인 요청이 들어오면, 예외가 발생해야한다.")
            void null_생일_요청_멤버_등록_실패_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        "테스트1팀",
                        null,
                        LocalDate.of(2024, 2, 29)
                );

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("근무 시작일 값이 null인 요청이 들어오면, 예외가 발생해야한다.")
            void null_근무_시작일_요청_멤버_등록_실패_테스트() throws Exception {
                // given
                MemberRegistrationRequest request = MemberRegistrationRequest.of(
                        "테스트팀원1",
                        "테스트1팀",
                        LocalDate.of(1999, 9, 9),
                        null
                );

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }
        }
    }

    private String stringify(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}
