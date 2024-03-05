package org.example.yeonghuns.dto.commute.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
public record GetCommuteRecordRequest(@NotNull long id, @NotNull YearMonth yearMonth) {
}
