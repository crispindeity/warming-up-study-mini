package study.crispin.member.application.service;

import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.request.MemberUpdateRequest;
import study.crispin.member.presentation.response.MemberRegistrationResponse;
import study.crispin.member.presentation.response.MemberRetrieveResponses;
import study.crispin.member.presentation.response.MemberUpdateResponse;

public interface MemberService {
    MemberRegistrationResponse register(MemberRegistrationRequest request);

    MemberUpdateResponse updateRole(MemberUpdateRequest request);

    MemberRetrieveResponses retrieve();
}
