package study.crispin.member.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.crispin.common.exception.ExceptionMessage;
import study.crispin.common.exception.VerificationException;
import study.crispin.member.application.request.MemberRegistrationRequest;
import study.crispin.member.application.request.MemberUpdateRequest;
import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.repository.MemberRepository;
import study.crispin.member.presentation.response.MemberRegistrationResponse;
import study.crispin.member.presentation.response.MemberRetrieveResponse;
import study.crispin.member.presentation.response.MemberRetrieveResponses;
import study.crispin.member.presentation.response.MemberUpdateResponse;
import study.crispin.team.infrastructure.repository.TeamRepository;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public MemberServiceImpl(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public MemberRegistrationResponse register(MemberRegistrationRequest request) {
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

    @Override
    @Transactional
    public MemberUpdateResponse updateRole(MemberUpdateRequest request) {
        Member findedMember = memberRepository.findByNameAndBirthdayAndWorkStartDate(
                        request.name(),
                        request.birthday(),
                        request.workStartDate())
                .orElseThrow();

        verifyTeamAffiliation(findedMember);
        verifyRegisteredManager(findedMember);

        Member updatedMember = findedMember.updateRole();
        Member savedMember = memberRepository.save(updatedMember);

        teamRepository.updateTeamManager(savedMember);

        return MemberUpdateResponse.of(savedMember.name(), savedMember.teamName(), savedMember.role());
    }

    @Override
    public MemberRetrieveResponses retrieve() {
        List<Member> findMembers = memberRepository.findAll();

        List<MemberRetrieveResponse> responses = findMembers.stream()
                .map(member -> MemberRetrieveResponse.of(
                        member.name(),
                        member.teamName(),
                        member.role(),
                        member.birthday(),
                        member.workStartDate()))
                .toList();

        return MemberRetrieveResponses.of(responses);
    }

    private void verifyUnregisteredTeamName(String teamName) {
        if (teamName == null) {
            return;
        }
        if (!teamRepository.existsByName(teamName)) {
            throw new VerificationException(ExceptionMessage.TEAM_NAME_DOES_NOT_EXISTS);
        }
    }

    private void verifyAlreadyRegisteredMember(String name, LocalDate birthday, LocalDate workStartDate) {
        if (memberRepository.existsByNameAndBirthdayAndWorkStartDate(name, birthday, workStartDate)) {
            throw new VerificationException(ExceptionMessage.MEMBER_ALREADY_EXISTS);
        }
    }

    private void verifyTeamAffiliation(Member member) {
        if (member.teamName() == null) {
            throw new VerificationException(ExceptionMessage.NOT_MEMBER_OF_TEAM);
        }
    }

    private void verifyRegisteredManager(Member member) {
        teamRepository.findByName(member.teamName()).ifPresent(
                team -> {
                    if (team.isRegisteredManager(member.name())) {
                        throw new VerificationException(ExceptionMessage.ALREADY_MANAGER_REGISTERED);
                    }
                }
        );
    }
}
