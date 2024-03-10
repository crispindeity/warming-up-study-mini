package inflearn.mini.annualleave.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inflearn.mini.annualleave.dto.request.AnnualLeaveRequestDto;
import inflearn.mini.annualleave.service.AnnualLeaveService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/annual-leaves")
public class AnnualLeaveController {

    private final AnnualLeaveService annualLeaveService;

    @PostMapping
    public void requestAnnualLeave(@RequestBody final AnnualLeaveRequestDto request) {
        annualLeaveService.requestAnnualLeave(request);
    }
}
