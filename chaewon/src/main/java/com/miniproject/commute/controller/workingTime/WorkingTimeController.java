package com.miniproject.commute.controller.workingTime;

import com.miniproject.commute.dto.workingTime.request.WorkingTimeRequest;
import com.miniproject.commute.dto.workingTime.response.WorkingTimeResponse;
import com.miniproject.commute.service.workingTime.WorkingTimeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkingTimeController {
    private final WorkingTimeService workingTimeService;

    public WorkingTimeController(WorkingTimeService workingTimeService) {
        this.workingTimeService = workingTimeService;
    }

    @GetMapping("working-time")
    public WorkingTimeResponse getWorkingTime(@RequestBody @Valid WorkingTimeRequest workingTimeRequest){
        return workingTimeService.getWorkingTime(workingTimeRequest);
    }
}
