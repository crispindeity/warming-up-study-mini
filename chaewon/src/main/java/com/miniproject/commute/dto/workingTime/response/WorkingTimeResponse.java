package com.miniproject.commute.dto.workingTime.response;

import lombok.Builder;

import java.util.List;

public record WorkingTimeResponse(String name, List<WorkingTimeResponseDetail> detail, int sum) {
    @Builder
    public WorkingTimeResponse(String name, List<WorkingTimeResponseDetail> detail, int sum) {
        this.name = name;
        this.detail = detail;
        this.sum = sum;
    }
}
