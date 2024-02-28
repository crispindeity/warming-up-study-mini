package study.crispin.member.application.service;

import org.springframework.util.Assert;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.member.presentation.port.MemberService;
import study.crispin.member.presentation.response.MemberRegistrationResponse;
import study.crispin.team.infrastructure.repository.TeamRepository;

import java.time.LocalDate;

public class MemberServiceImpl implements MemberService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public MemberServiceImpl(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberRegistrationResponse registration(MemberRegistrationRequest request) {
        Assert.notNull(request, "요청값은 필수입니다.");
        Assert.notNull(request.name(), "이름은 필수입니다.");

        verifyUnregisteredTeamName(request.teamName());
        verifyAlreadyRegisteredMember(request.name(), request.birthday(), request.workStartDate());

        Member member = Member.of(request.name(), request.teamName(), request.birthday(), request.workStartDate());
        Member savedMember = memberRepository.save(member);

        return MemberRegistrationResponse.of(
                savedMember.id(),
                savedMember.name(),
                savedMember.teamName(),
                savedMember.role(),
                savedMember.birthday(),
                savedMember.workStartDate()
        );
    }

    private void verifyUnregisteredTeamName(String teamName) {
        if (teamName == null) {
            return;
        }
        teamRepository.findByName(teamName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 이름입니다."));
    }

    private void verifyAlreadyRegisteredMember(String name, LocalDate birthday, LocalDate workStartDate) {
        memberRepository.findByNameAndBirthdayAndworkStartDate(name, birthday, workStartDate).ifPresent(
            member -> {
                throw new IllegalArgumentException("이미 존재하는 멤버입니다.");
            }
        );
    }
}
