package org.example.yeonghuns.dto.commute.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.YearMonth;

/**
 * packageName    : org.example.yeonghuns.dto.commute.request
 * fileName       : GetCommuteRecordRequest
 * author         : Yeong-Huns
 * date           : 2024-03-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-05        Yeong-Huns       최초 생성
 */
public record GetCommuteRecordRequest(@NotNull long id, @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {
    public int getYear(){ return this.yearMonth.getYear();}
    public int getMonth(){ return this.yearMonth.getMonth().getValue(); }
}
