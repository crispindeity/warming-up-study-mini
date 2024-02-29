package study.crispin.member.presentation.response;

import study.crispin.member.domain.Role;

public record MemberUpdateResponse(String name, String teamName, Role role) {

    public static MemberUpdateResponse of (String name, String teamName, Role role) {
        return new MemberUpdateResponse(name, teamName, role);
    }
}
