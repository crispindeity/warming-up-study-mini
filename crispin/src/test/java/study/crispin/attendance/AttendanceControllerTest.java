package study.crispin.attendance;

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
import study.crispin.attendance.application.AttendanceService;
import study.crispin.attendance.application.request.ClockInOrOutRequest;
import study.crispin.attendance.application.request.WorkHoursInquiryRequest;
import study.crispin.attendance.presentation.controller.AttendanceController;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.attendance.presentation.response.WorkHoursInquiryResponse;
import study.crispin.attendance.presentation.response.WorkHoursInquiryResponses;
import study.crispin.common.DateTimeHolder;
import study.crispin.common.controller.ExceptionControllerAdvice;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@WebMvcTest(AttendanceController.class)
@DisplayName("출,퇴근 컨트롤러 테스트")
public class AttendanceControllerTest {

    @MockBean
    private DateTimeHolder dateTimeHolder;
    @MockBean
    private AttendanceService attendanceService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public AttendanceControllerTest() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AttendanceController(dateTimeHolder, attendanceService))
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Nested
    @DisplayName("출근 등록 컨트롤러 테스트")
    class ClockInControllerTest {

        @Nested
        @DisplayName("출근 등록 성공 테스트")
        class ClockInSuccessTest {

            @Test
            @DisplayName("정상적인 출근 등록 요청 시, 요청 처리 후 정상 응답을 반환해야 한다.")
            void 출근_등록_성공_테스트() throws Exception {
                // given
                Long memberId = 1L;
                LocalDateTime now = LocalDateTime.of(2024, 2, 29, 9, 0, 0);

                ClockInOrOutRequest request = ClockInOrOutRequest.of(memberId);
                ClockInResponse response = ClockInResponse.of(1L, "테스트팀원1", now);

                BDDMockito.given(dateTimeHolder.now()).willReturn(now);
                BDDMockito.given(attendanceService.clockIn(memberId, dateTimeHolder.now())).willReturn(response);

                // when
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clock-in")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().json(stringify(response)));

                // then
                BDDMockito.then(attendanceService).should().clockIn(memberId, now);
            }
        }
    }

    @Nested
    @DisplayName("퇴근 등록 컨트롤러 테스트")
    class ClockOutControllerTest {

        @Nested
        @DisplayName("퇴근 등록 성공 테스트")
        class ClockOutSuccessTest {

            @Test
            @DisplayName("정상적인 퇴근 등록 요청 시, 요청 처리 후 정상 응답을 반환해야 한다.")
            void 퇴근_등록_성공_테스트() throws Exception {
                // given
                Long memberId = 1L;
                LocalDateTime now = LocalDateTime.of(2024, 2, 29, 18, 0, 0);

                ClockInOrOutRequest request = ClockInOrOutRequest.of(memberId);
                ClockOutResponse response = ClockOutResponse.of(
                        1L,
                        "테스트팀원1",
                        now.minusHours(9L),
                        now
                );

                BDDMockito.given(dateTimeHolder.now()).willReturn(now);
                BDDMockito.given(attendanceService.clockOut(memberId, dateTimeHolder.now())).willReturn(response);

                // when
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/clock-out")
                                .content(stringify(request))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().json(stringify(response)));

                // then
                BDDMockito.then(attendanceService).should().clockOut(memberId, now);
            }
        }
    }

    @Nested
    @DisplayName("근무 시간 조회 컨트롤러 테스트")
    class WorkHoursInquiryControllerTest {

        @Nested
        @DisplayName("근무 시간 조회 성공 테스트")
        class WorkHoursInquirySuccessTest {

            @Test
            @DisplayName("정상적인 근무 시간 조회 요청 시, 요청 처리 후 정상 응답을 반환해야 한다.")
            void 근무_시간_조회_성공_테스트() throws Exception {
                // given
                String memberId = "1";
                LocalDateTime workDateTime = LocalDateTime.of(
                        LocalDate.of(2024, 2, 1), LocalTime.MIN
                );

                WorkHoursInquiryRequest request = WorkHoursInquiryRequest.of(
                        memberId,
                        "2024-02"
                );
                WorkHoursInquiryResponses responses = WorkHoursInquiryResponses.of(
                        List.of(WorkHoursInquiryResponse.of(workDateTime, 540)), 540);

                BDDMockito.given(attendanceService.workHoursInquiry(request)).willReturn(responses);

                // when
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/work-hours")
                                .queryParam("member-id", request.memberId())
                                .queryParam("date", request.date())
                                .characterEncoding(StandardCharsets.UTF_8)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().json(stringify(responses)));

                // then
                BDDMockito.then(attendanceService).should().workHoursInquiry(request);
            }
        }

        @Nested
        @DisplayName("근무 시간 조회 실패 테스트")
        class WorkHoursInquiryFailTest {

            @Test
            @DisplayName("날짜 값이 잘못된 형식인 요청이 들어오면, 예외가 발생해야 한다.")
            void 잘못된_날짜_요청_근무_시간_조회_실패_테스트() throws Exception {
                // given
                String memberId = "1";
                String wrongDate = "1989-02";

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/work-hours")
                                .queryParam("memberId", memberId)
                                .queryParam("date", wrongDate)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("아이디 값이 비어있는 요청이 들어오면, 예외가 발생해야 한다.")
            void 비어있는_아이디_요청_근무_시간_조회_실패_테스트() throws Exception {
                // given
                String wrongId = "";
                String date = "2024-01";

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/work-hours")
                                .queryParam("memberId", wrongId)
                                .queryParam("date", date)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("아이디 값이 null 인 요청이 들어오면, 예외가 발생해야 한다.")
            void null_아이디_요청_근무_시간_조회_실패_테스트() throws Exception {
                // given
                String wrongId = null;
                String date = "2024-01";

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/work-hours")
                                .queryParam("memberId", wrongId)
                                .queryParam("date", date)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
            }

            @Test
            @DisplayName("아이디 값이 공백 인 요청이 들어오면, 예외가 발생해야 한다.")
            void 공백_아이디_요청_근무_시간_조회_실패_테스트() throws Exception {
                // given
                String wrongId = " ";
                String date = "2024-01";

                // when & then
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/work-hours")
                                .queryParam("memberId", wrongId)
                                .queryParam("date", date)
                                .characterEncoding(StandardCharsets.UTF_8)
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
