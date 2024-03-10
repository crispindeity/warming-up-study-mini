package org.example.yeonghuns.dto.annualLeave.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.example.yeonghuns.domain.AnnualLeave;
import org.example.yeonghuns.domain.Member;

import java.time.LocalDate;

/**
 * packageName    : org.example.yeonghuns.dto.annual.request
 * fileName       : RegistAnnualRequest
 * author         : Yeong-Huns
 * date           : 2024-03-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-07        Yeong-Huns       최초 생성
 */
public record RegisterAnnualLeaveRequest(@NotNull long id, @Future LocalDate date) {
    public AnnualLeave toEntity(Member member){
        return AnnualLeave.builder()
                .annualLeaveDate(date)
                .member(member)
                .build();
    }
}
