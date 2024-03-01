package study.crispin.member.presentation.response;

import java.util.List;

public record MemberRetrieveResponses(List<MemberRetrieveResponse> responses) {
    public static MemberRetrieveResponses of(List<MemberRetrieveResponse> responses) {
        return new MemberRetrieveResponses(responses);
    }
}
