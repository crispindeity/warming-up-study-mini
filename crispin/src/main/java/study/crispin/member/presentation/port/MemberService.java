package study.crispin.member.presentation.port;

import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.request.MemberUpdateRequest;
import study.crispin.member.presentation.response.MemberRegistrationResponse;
import study.crispin.member.presentation.response.MemberUpdateResponse;

public interface MemberService {
    MemberRegistrationResponse registration(MemberRegistrationRequest request);

    MemberUpdateResponse updateRole(MemberUpdateRequest request);
}
