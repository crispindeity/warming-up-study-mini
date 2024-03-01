package study.crispin.team.presentation.response;

import java.util.List;

public record TeamRetrieveResponses(List<TeamRetrieveResponse> teamRetrieveResponses) {
    public static TeamRetrieveResponses of(List<TeamRetrieveResponse> responses) {
        return new TeamRetrieveResponses(responses);
    }
}
