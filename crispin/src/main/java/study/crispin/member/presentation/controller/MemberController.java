package study.crispin.member.presentation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.request.MemberUpdateRequest;
import study.crispin.member.application.service.MemberService;
import study.crispin.member.presentation.response.MemberRegistrationResponse;
import study.crispin.member.presentation.response.MemberRetrieveResponses;
import study.crispin.member.presentation.response.MemberUpdateResponse;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public ResponseEntity<MemberRetrieveResponses> retrieve() {
        return ResponseEntity.ok(memberService.retrieve());
    }

    @PostMapping("/member")
    public ResponseEntity<MemberRegistrationResponse> registration(
            @RequestBody @Valid MemberRegistrationRequest request
    ) {
        return ResponseEntity.ok(memberService.registration(request));
    }

    @PutMapping("/member")
    public ResponseEntity<MemberUpdateResponse> update(
            @RequestBody @Valid MemberUpdateRequest request
    ) {
        return ResponseEntity.ok(memberService.updateRole(request));
    }
}
