package org.example.yeonghuns.dto.annualLeave.request;

import jakarta.validation.constraints.NotNull;

/**
 * packageName    : org.example.yeonghuns.dto.annual.request
 * fileName       : GetRemainAnnualLeaves
 * author         : Yeong-Huns
 * date           : 2024-03-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-07        Yeong-Huns       최초 생성
 */
public record GetRemainAnnualLeavesRequest(@NotNull long id) {
}
