package study.crispin.attendance.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WorkHoursInquiryResponses(
        @JsonProperty(value = "detail")
        List<WorkHoursInquiryResponse> workHoursInquiryResponses,
        long sum
) {

    public static WorkHoursInquiryResponses of(List<WorkHoursInquiryResponse> workHoursInquiryResponses, long sum) {
        return new WorkHoursInquiryResponses(workHoursInquiryResponses, sum);
    }
}
