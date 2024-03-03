package study.crispin.team.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TeamRetrieveResponses(
        @JsonProperty(value = "result")
        List<TeamRetrieveResponse> teamRetrieveResponses
) {
    public static TeamRetrieveResponses of(List<TeamRetrieveResponse> responses) {
        return new TeamRetrieveResponses(responses);
    }
}
