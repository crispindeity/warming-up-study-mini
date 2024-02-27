package org.example.yeonghuns.service.member;

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
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamService teamService;

    public MemberService(MemberRepository memberRepository, TeamService teamService) {
        this.memberRepository = memberRepository;
        this.teamService = teamService;
    }

    @Transactional
    public void saveMember(SaveMemberRequest request) {
        Team team = teamService.findTeamByName(request);
        memberRepository.save(request.toEntity(team));
    }

    public List<GetAllMembersResponse> getAllMembers() {
        return memberRepository.findAll().stream().map(Member::toResponse).toList();
    }
}
