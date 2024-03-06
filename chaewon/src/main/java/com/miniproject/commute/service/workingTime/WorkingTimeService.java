package com.miniproject.commute.service.workingTime;

import com.miniproject.commute.domain.Member;
import com.miniproject.commute.domain.Team;
import com.miniproject.commute.domain.WorkingTime;
import com.miniproject.commute.domain.WorkingTimePK;
import com.miniproject.commute.dto.workingTime.request.DayOffApplicationRequest;
import com.miniproject.commute.dto.workingTime.request.WorkingTimeRequest;
import com.miniproject.commute.dto.workingTime.response.WorkingTimeResponseDetail;
import com.miniproject.commute.dto.workingTime.response.WorkingTimeResponse;
import com.miniproject.commute.repository.member.MemberRepository;
import com.miniproject.commute.repository.team.TeamRepository;
import com.miniproject.commute.repository.workingTime.WorkingTimeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class WorkingTimeService {
    private final MemberRepository memberRepository;
    private final WorkingTimeRepository workingTimeRepository;
    private  final TeamRepository teamRepository;

    /**
     *
     * @param request
     * @return
     * 1. 존재하는 직원인가?
     * 2. 입력 받은 연도와 월의 값이 정상적인가? -> 조회 가능한 기간에 제한(미구현)
     * 3. 1-2 통과 시 조회 결과 반환
     */
    @Transactional(readOnly = true)
    public WorkingTimeResponse getWorkingTime(WorkingTimeRequest request) {

        checkMemberExist(request.memberId());

        LocalDate monthStartDay = LocalDate.parse(request.yearAndMonth()+"-01");
        LocalDate monthEndDay = monthStartDay.plusMonths(1).minusDays(1);

        Member member = memberRepository.findById(request.memberId()).get();

        List<WorkingTime> workingTimes = workingTimeRepository.findAllByWorkingTimePK_MemberIdAndWorkingTimePK_WorkingDateBetween(request.memberId(), monthStartDay, monthEndDay);
        List<WorkingTimeResponseDetail> listForWorkingTimeResponses =
                workingTimes.stream()
                        .map(m ->
                                WorkingTimeResponseDetail.builder()
                                        .workingDate(m.getWorkingTimePK().getWorkingDate())
                                        .workingMinute(m.getWorkingMinutes())
                                        .usingDayOff(m.isUsingDayOff())
                                        .build())
                        .toList();

        int sum = workingTimes.stream().mapToInt(WorkingTime::getWorkingMinutes).sum();

        return WorkingTimeResponse.builder().name(member.getName()).detail(listForWorkingTimeResponses).sum(sum).build();
    }

    /**
     * 직원의 연가 신청
     *
     * 1. 직원 존재 여부
     * 2. 직원의 연차 횟수가 남아있는지 확인
     * 3. 직원이 소속된 팀 테이블에서 휴가 신청 기한을 확인
     * 4. 직원의 휴가 신청일과 휴가 신청 기한을 비교 후 수리 여부 처리
     * 5. 1-3 통과 시 workingTime 테이블에 저장 후 멤버의 연차 횟수 차감
     */
    @Transactional
    public void applyDayOff(DayOffApplicationRequest request){

        checkMemberExist(request.memberId());

        Member member = memberRepository.findById(request.memberId()).get();

        if(workingTimeRepository.existsByWorkingTimePK_MemberIdAndWorkingTimePK_WorkingDateAndUsingDayOff(request.memberId(), request.dateOfDayOff(), true)){
            throw new IllegalArgumentException("이미 연차를 신청한 날짜입니다.");
        }

        if(member.getNumberOfDayOff()<=0){
            throw new IllegalArgumentException("금년 연차 횟수가 모두 소진되었습니다.");
        }

        Team team = teamRepository.findById(member.getId()).orElseThrow(IllegalArgumentException::new);


        //연차 신청 기한을 지켰는가?
        if (!request.dateOfDayOff().isAfter(LocalDate.now().plusDays(team.getDayOffPeriod()))){
            throw new IllegalArgumentException("팀의 연차 신청 기간이 지났습니다.");
        }

        WorkingTime workingTime = WorkingTime.builder()
                .workingTimePK(WorkingTimePK.builder()
                        .memberId(request.memberId())
                        .workingDate(request.dateOfDayOff())
                        .build())
                .workingMinutes(0)
                .build();
        workingTime.useDayOff();
        workingTimeRepository.save(workingTime);
        member.useDayOff();
    }

    private void checkMemberExist(long id) {
        if(!memberRepository.existsById(id)){
            throw new IllegalArgumentException("존재하지 않는 멤버입니다.");
        }
    }
}