package org.example.yeonghuns.dto.commute.response;

import lombok.Builder;
import org.example.yeonghuns.domain.AnnualLeave;
import org.example.yeonghuns.domain.Commute;

import java.time.Duration;
import java.time.LocalDate;

/**
 * packageName    : org.example.yeonghuns.dto.commute.response
 * fileName       : GetCommuteRecordResponse
 * author         : Yeong-Huns
 * date           : 2024-03-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-05        Yeong-Huns       최초 생성
 */
@Builder
public record GetCommuteDetail(LocalDate date, long workingMinutes, boolean usingDayOff) {
    public static GetCommuteDetail from(Commute commute){
        Duration duration = Duration.between(commute.getCreatedAt(), commute.getUpdatedAt());

        return GetCommuteDetail.builder()
                .date(commute.getCreatedAt().toLocalDate())
                .workingMinutes(duration.toMinutes())
                .usingDayOff(false)
                .build();
    }
    public static GetCommuteDetail from(AnnualLeave annualLeave){
        return GetCommuteDetail.builder()
                .date(annualLeave.getAnnualDateLeave())
                .workingMinutes(0)
                .usingDayOff(true)
                .build();
    }
}
