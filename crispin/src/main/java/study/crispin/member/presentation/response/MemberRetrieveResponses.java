package study.crispin.member.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MemberRetrieveResponses(
        @JsonProperty(value = "result")
        List<MemberRetrieveResponse> responses
) {
    public static MemberRetrieveResponses of(List<MemberRetrieveResponse> responses) {
        return new MemberRetrieveResponses(responses);
    }
}
