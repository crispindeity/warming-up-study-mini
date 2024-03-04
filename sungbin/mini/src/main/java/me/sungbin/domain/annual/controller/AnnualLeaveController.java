package me.sungbin.domain.annual.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sungbin.domain.annual.model.request.AnnualLeaveRequestDto;
import me.sungbin.domain.annual.service.AnnualLeaveService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.annual.controller
 * @fileName : AnnualLeaveController
 * @date : 3/5/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/5/24       rovert         최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/annual-leave")
public class AnnualLeaveController {

    private final AnnualLeaveService annualLeaveService;

    @PostMapping
    public void applyAnnualLeave(@Valid @RequestBody AnnualLeaveRequestDto requestDto) {
        this.annualLeaveService.applyAnnualLeave(requestDto);
    }
}
