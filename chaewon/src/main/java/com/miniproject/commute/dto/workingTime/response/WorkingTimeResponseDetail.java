package com.miniproject.commute.dto.workingTime.response;

import lombok.Builder;

import java.time.LocalDate;

public record WorkingTimeResponseDetail(LocalDate workingDate, int workingMinute, boolean usingDayOff) {
    @Builder
    public WorkingTimeResponseDetail(LocalDate workingDate, int workingMinute, boolean usingDayOff) {
        this.workingDate = workingDate;
        this.workingMinute = workingMinute;
        this.usingDayOff = usingDayOff;
    }
}
