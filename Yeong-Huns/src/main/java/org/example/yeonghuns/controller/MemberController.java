package org.example.yeonghuns.controller;

import org.example.yeonghuns.dto.member.request.SaveMemberRequest;
import org.example.yeonghuns.dto.member.response.GetAllMembersResponse;
import org.example.yeonghuns.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public ResponseEntity<Void> saveMember(@RequestBody SaveMemberRequest request) {
        System.out.println(request.getTeamname());
        memberService.saveMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/member")
    public ResponseEntity<List<GetAllMembersResponse>> getAllMembers() {
        List<GetAllMembersResponse> allMembersList = memberService.getAllMembers();
        return ResponseEntity.ok().body(allMembersList);
    }


}
