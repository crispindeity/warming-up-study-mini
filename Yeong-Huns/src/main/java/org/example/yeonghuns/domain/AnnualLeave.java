package org.example.yeonghuns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * packageName    : org.example.yeonghuns.domain
 * fileName       : Annual
 * author         : Yeong-Huns
 * date           : 2024-03-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-06        Yeong-Huns       최초 생성
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnualLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate annualDateLeave;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public AnnualLeave(LocalDate annualDateLeave, Member member) {
        this.annualDateLeave = annualDateLeave;
        this.member = member;
    }
}
