package study.crispin.member.presentation.port;

import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.presentation.response.MemberRegistrationResponse;

public interface MemberService {
    MemberRegistrationResponse registration(MemberRegistrationRequest request);
}
