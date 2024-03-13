package inflearn.mini.annualleave.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inflearn.mini.annualleave.dto.request.AnnualLeaveRequestDto;
import inflearn.mini.annualleave.dto.request.RemainingAnnualLeaveRequestDto;
import inflearn.mini.annualleave.dto.response.RemainingAnnualLeaveResponseDto;
import inflearn.mini.annualleave.service.AnnualLeaveService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/annual-leaves")
public class AnnualLeaveController {

    private final AnnualLeaveService annualLeaveService;

    @PostMapping
    public void requestAnnualLeave(@RequestBody @Valid final AnnualLeaveRequestDto request) {
        annualLeaveService.requestAnnualLeave(request);
    }

    @GetMapping
    public ResponseEntity<RemainingAnnualLeaveResponseDto> getRemainingAnnualLeave(
            @RequestBody @Valid final RemainingAnnualLeaveRequestDto request
    ) {
        final RemainingAnnualLeaveResponseDto remainingAnnualLeave = annualLeaveService.getRemainingAnnualLeave(request);
        return ResponseEntity.ok().body(remainingAnnualLeave);
    }
}
