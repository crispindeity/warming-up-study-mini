package org.example.yeonghuns.service.member;

import lombok.RequiredArgsConstructor;
import org.example.yeonghuns.domain.Member;
import org.example.yeonghuns.domain.Team;
import org.example.yeonghuns.dto.member.request.SaveMemberRequest;
import org.example.yeonghuns.dto.member.response.GetAllMembersResponse;
import org.example.yeonghuns.repository.MemberRepository;
import org.example.yeonghuns.service.team.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamService teamService;

    @Transactional
    public void saveMember(SaveMemberRequest request) {
        Team team = teamService.findTeamByName(request);
        Member member = memberRepository.save(request.toEntity(team));
        if(request.isManager()) teamService.updateManager(team, member);
    }

    @Transactional(readOnly = true)
    public List<GetAllMembersResponse> getAllMembers() {
        return memberRepository.findAll().stream().map(GetAllMembersResponse::from).toList();
    }
}
