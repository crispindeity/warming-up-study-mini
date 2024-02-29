package study.crispin.member.application.request;

import java.time.LocalDate;

public record MemberUpdateRequest(String name, LocalDate birthday, LocalDate workStartDate) {

    public static MemberUpdateRequest of(String name, LocalDate birthday, LocalDate workStartDate) {
        return new MemberUpdateRequest(name, birthday, workStartDate);
    }
}
