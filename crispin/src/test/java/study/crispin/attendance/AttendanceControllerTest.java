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
import study.crispin.attendance.presentation.controller.AttendanceController;
import study.crispin.attendance.presentation.response.ClockInResponse;
import study.crispin.attendance.presentation.response.ClockOutResponse;
import study.crispin.common.DateTimeHolder;
import study.crispin.common.controller.ExceptionControllerAdvice;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

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

    private String stringify(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}
