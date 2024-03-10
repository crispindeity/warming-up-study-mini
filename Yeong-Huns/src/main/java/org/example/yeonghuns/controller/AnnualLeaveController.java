package org.example.yeonghuns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.yeonghuns.dto.annualLeave.request.GetRemainAnnualLeavesRequest;
import org.example.yeonghuns.dto.annualLeave.request.RegisterAnnualLeaveRequest;
import org.example.yeonghuns.dto.annualLeave.response.GetRemainAnnualLeavesResponse;
import org.example.yeonghuns.service.annual.AnnualLeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : org.example.yeonghuns.controller
 * fileName       : AnnualController
 * author         : Yeong-Huns
 * date           : 2024-03-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-06        Yeong-Huns       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class AnnualLeaveController {
    private final AnnualLeaveService annualLeaveService;

    @PostMapping("/annual")
    public ResponseEntity<Void> registerAnnualLeave(@RequestBody @Valid RegisterAnnualLeaveRequest request) {
        annualLeaveService.registerAnnualLeave(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/annual")
    public ResponseEntity<GetRemainAnnualLeavesResponse> getRemainAnnualLeaves(@Valid GetRemainAnnualLeavesRequest request) {
        long remainAnnualLeaves = annualLeaveService.getRemainAnnualLeaves(request);
        GetRemainAnnualLeavesResponse response = new GetRemainAnnualLeavesResponse(remainAnnualLeaves);
        return ResponseEntity.ok(response);
    }
}
