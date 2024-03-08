package org.example.yeonghuns.service.annual;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.yeonghuns.config.Error.exception.annualLeave.AcceptTeamPolicyException;
import org.example.yeonghuns.config.Error.exception.annualLeave.AlreadyRegisteredException;
import org.example.yeonghuns.config.Error.exception.annualLeave.RemainAnnualLeavesException;
import org.example.yeonghuns.domain.AnnualLeave;
import org.example.yeonghuns.domain.JoinDate;
import org.example.yeonghuns.domain.Member;
import org.example.yeonghuns.dto.annualLeave.request.GetRemainAnnualLeavesRequest;
import org.example.yeonghuns.dto.annualLeave.request.RegisterAnnualLeaveRequest;
import org.example.yeonghuns.repository.AnnualLeaveRepository;
import org.example.yeonghuns.service.member.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * packageName    : org.example.yeonghuns.service.annual
 * fileName       : AnnualService
 * author         : Yeong-Huns
 * date           : 2024-03-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-06        Yeong-Huns       최초 생성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AnnualLeaveService {
    private final AnnualLeaveRepository annualLeaveRepository;
    private final MemberService memberService;

    @Transactional
    public void registerAnnualLeave(RegisterAnnualLeaveRequest request){
        Member member = memberService.findMemberById(request.id());
        if(isAcceptTeamPolicy(member, request)) throw new AcceptTeamPolicyException();
        if(isAlreadyUsingAnnualLeaves(member, request.date())) throw new AlreadyRegisteredException();
        if(isRemainAnnualLeaves(member)) throw new RemainAnnualLeavesException();

        annualLeaveRepository.save(request.toEntity(member));
    }
    @Transactional(readOnly = true)
    public long getRemainAnnualLeaves(GetRemainAnnualLeavesRequest request){
        Member member = memberService.findMemberById(request.id());
        return remainAnnualLeaves(member);
    }

    private boolean isAcceptTeamPolicy(Member member, RegisterAnnualLeaveRequest request){
        return
        ChronoUnit.DAYS.between(LocalDate.now(), request.date()) < member.getTeam().getDayBeforeAnnual();
    }
    private long remainAnnualLeaves(Member member){ // 남은 연차 계산 & 연차 조회시 반환
        long maxAnnualLeave = ChronoUnit.YEARS
                .between(member.getCreatedAt(), LocalDateTime.now()) >= 1 ?
                JoinDate.OVER_ONE_YEAR.getAnnualLeaves() : JoinDate.UNDER_ONE_YEAR.getAnnualLeaves();

        long usedThisYear = annualLeaveRepository.countByMemberId(member.getId(), YearMonth.now().getYear());
        return maxAnnualLeave - usedThisYear;
    }
    private boolean isRemainAnnualLeaves(Member member){
        return remainAnnualLeaves(member) <= 0;
    }

    public boolean isAlreadyUsingAnnualLeaves(Member member, LocalDate date){
        return annualLeaveRepository.existsByMemberIdAndAnnualDateLeaveEquals(member.getId(), date);
    }
    public List<AnnualLeave> findAnnualLeavesByMemberIdAndYearMonth(long memberId, YearMonth request){
        int year = request.getYear();
        int month = request.getMonth().getValue();
        //
        return annualLeaveRepository.findAllAnnualLeavesByMemberIdAndYearMonth(memberId, year, month);
    }
}
