package com.miniproject.commute.dto.workingTime.response;

import lombok.Builder;

import java.time.LocalDate;

public record WorkingTimeResponseDetail(LocalDate workingDate, int workingMinute) {
    @Builder
    public WorkingTimeResponseDetail(LocalDate workingDate, int workingMinute) {
        this.workingDate = workingDate;
        this.workingMinute = workingMinute;
    }
}
