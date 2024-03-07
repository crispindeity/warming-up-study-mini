package com.miniproject.commute.controller.member;

import com.miniproject.commute.dto.member.request.ChooseManagerRequest;
import com.miniproject.commute.dto.member.request.MemberSaveRequest;
import com.miniproject.commute.dto.member.response.MemberResponse;
import com.miniproject.commute.service.member.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public void saveMember(@RequestBody @Valid MemberSaveRequest request){
        memberService.saveMember(request);
    }

    @PutMapping("/member")
    public void chooseManager(@RequestBody @Valid ChooseManagerRequest request){
        memberService.chooseManager(request);
    }

    @GetMapping("/member")
    public List<MemberResponse> getMembers(){
        return memberService.getMembers();
    }
}
