package com.miniproject.commute.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkingTime {
    //id로 특정 데이터를 식별할 의미가 없어보여 복합키 사용
    @EmbeddedId
    private WorkingTimePK workingTimePK;
    @Column(nullable = false)
    private Integer workingMinutes;
    private boolean usingDayOff = false;

    @Builder
    public WorkingTime(WorkingTimePK workingTimePK, Integer workingMinutes) {
        this.workingTimePK = workingTimePK;
        this.workingMinutes = workingMinutes;
    }

    public void useDayOff(){
        this.usingDayOff = !this.usingDayOff;
    }
}
