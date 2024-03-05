package com.miniproject.commute.service.member;

import com.miniproject.commute.domain.Member;
import com.miniproject.commute.domain.Role;
import com.miniproject.commute.domain.Team;
import com.miniproject.commute.dto.member.request.ChooseManagerRequest;
import com.miniproject.commute.dto.member.request.MemberSaveRequest;
import com.miniproject.commute.dto.member.response.MemberResponse;
import com.miniproject.commute.repository.member.MemberRepository;
import com.miniproject.commute.repository.team.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public MemberService(MemberRepository memberRepository, TeamRepository teamRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    /**
     *
     * @param request
     * 멤버: team_id를 필수 지정해 저장
     * team_id 지정 시, 해당 팀이 존재하는지 해당 팀에 이미 매니저가 존재하는지 체크
     */
    @Transactional
    public void saveMember(MemberSaveRequest request) {

        Team team = teamRepository.findById(request.teamId()).orElseThrow(()->new IllegalArgumentException("존재하지 않는 팀입니다."));
        validateManagerExist(request.teamId(), request.isManager());
        Member member= Member.builder().name(request.name()).joinDate(request.joinDate()).birthday(request.birthday()).team(team).isManager(request.isManager()).build();

        memberRepository.save(member);
    }

    private void validateManagerExist(long memberTeamId, boolean isManager) {
        if(isManager&&memberRepository.existsByTeamIdAndIsManager(memberTeamId, true)){
            throw new IllegalArgumentException("이미 매니저가 지정된 팀입니다.");
        }
    }

    /**
     * 매니저 선정
     * @param request - id
     * 1. 매니저가 이미 존재하는 팀의 매니저를 변경할 경우
     * 2. 기존 매니저의 isManager=false로 변경
     */
    @Transactional
    public void chooseManager(ChooseManagerRequest request) {
        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사람입니다."));

        Member memberbyTeamIdAndIsManager = memberRepository.getByTeamIdAndIsManager(member.getTeam().getId(), true);

        if(memberbyTeamIdAndIsManager!=null){
            memberbyTeamIdAndIsManager.changeRole();
        }
        member.changeRole();
        }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {
        List<Member> all = memberRepository.findAllWithTeam();

        return all.stream()
                .map(m->MemberResponse.builder()
                        .name(m.getName())
                        .teamName(m.getTeam().getName())
                        .role(m.isManager()? Role.MANAGER:Role.MEMBER)
                        .birthday(m.getBirthday())
                        .build())
                .toList();
    }
}


