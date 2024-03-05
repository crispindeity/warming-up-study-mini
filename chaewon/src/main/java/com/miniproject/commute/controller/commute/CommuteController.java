package com.miniproject.commute.controller.commute;

import com.miniproject.commute.dto.commute.request.WorkInRequest;
import com.miniproject.commute.dto.commute.request.WorkOutRequest;
import com.miniproject.commute.service.commute.CommuteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommuteController {
    private final CommuteService commuteService;

    public CommuteController(CommuteService commuteService) {
        this.commuteService = commuteService;
    }

    /**
     * 출근 기록 저장 메서드
     * @param request
     * return - void(추후 정상적으로 저장시 메세지를 뿌리도록 변경할 가능성 있음)
     */
    @PostMapping("/commute")
    public void workInMember(@RequestBody @Valid WorkInRequest request){
        commuteService.workInMember(request);
    }

    /**
     * 퇴근 기록 저장 메서드
     * @param request
     * return - void(추후 정상적으로 저장시 메세지를 뿌리도록 변경할 가능성 있음)
     */
    @PutMapping ("/commute")
    public void workOutMember(@RequestBody @Valid WorkOutRequest request){
        commuteService.workOutMember(request);
    }
}
