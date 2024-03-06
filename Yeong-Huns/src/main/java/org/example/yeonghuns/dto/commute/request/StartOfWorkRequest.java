package org.example.yeonghuns.dto.commute.request;

import jakarta.validation.constraints.NotNull;
import org.example.yeonghuns.domain.Commute;
import org.example.yeonghuns.domain.Member;

/**
 * packageName    : org.example.yeonghuns.dto.commute.request
 * fileName       : RegisterCommuteRequest
 * author         : Yeong-Huns
 * date           : 2024-03-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-04        Yeong-Huns       최초 생성
 */
public record StartOfWorkRequest(@NotNull long id) {
    public Commute toEntity(Member member){
        return Commute.builder()
                .member(member)
                .build();
    }
}
