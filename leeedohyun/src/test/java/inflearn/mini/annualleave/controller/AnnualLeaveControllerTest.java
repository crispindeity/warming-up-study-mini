package inflearn.mini.annualleave.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import inflearn.mini.annualleave.dto.request.AnnualLeaveRequestDto;
import inflearn.mini.annualleave.exception.InvalidAnnualLeaveRequestException;
import inflearn.mini.annualleave.service.AnnualLeaveService;

@WebMvcTest(AnnualLeaveController.class)
class AnnualLeaveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AnnualLeaveService annualLeaveService;

    @Test
    void 연차를_신청한다() throws Exception {
        // given
        final AnnualLeaveRequestDto request = new AnnualLeaveRequestDto(1L, LocalDate.of(2024, 3, 15));

        // when
        doNothing().when(annualLeaveService).requestAnnualLeave(any());

        // then
        mockMvc.perform(post("/api/v1/annual-leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void 연차_신청에_실패한다() throws Exception {
        // given
        final AnnualLeaveRequestDto request = new AnnualLeaveRequestDto(1L, LocalDate.of(2024, 3, 15));

        // when
        doThrow(InvalidAnnualLeaveRequestException.class).when(annualLeaveService).requestAnnualLeave(any());

        // then
        mockMvc.perform(post("/api/v1/annual-leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
