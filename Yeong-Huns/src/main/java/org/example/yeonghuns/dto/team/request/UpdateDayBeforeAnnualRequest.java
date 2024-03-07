package org.example.yeonghuns.dto.team.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * packageName    : org.example.yeonghuns.dto.team.request
 * fileName       : UpdateDayBeforeAnnualRequest
 * author         : Yeong-Huns
 * date           : 2024-03-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-07        Yeong-Huns       최초 생성
 */
public record UpdateDayBeforeAnnualRequest(long id, @Positive int day) {
}
