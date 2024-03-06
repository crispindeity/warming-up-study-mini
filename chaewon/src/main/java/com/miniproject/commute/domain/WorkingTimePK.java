package com.miniproject.commute.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@EqualsAndHashCode
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkingTimePK implements Serializable {
    @Column(nullable = false)
    private Long memberId;
    @Column(nullable = false, columnDefinition = "date default (current_date)")
    private LocalDate workingDate;

    @Builder
    public WorkingTimePK(Long memberId, LocalDate workingDate) {
        this.memberId = memberId;
        this.workingDate = workingDate;
    }
}
