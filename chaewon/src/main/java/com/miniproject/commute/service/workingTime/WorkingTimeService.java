package com.miniproject.commute.service.workingTime;

import com.miniproject.commute.domain.Member;
import com.miniproject.commute.domain.WorkingTime;
import com.miniproject.commute.domain.WorkingTimePK;
import com.miniproject.commute.dto.workingTime.request.WorkingTimeRequest;
import com.miniproject.commute.dto.workingTime.response.WorkingTimeResponseDetail;
import com.miniproject.commute.dto.workingTime.response.WorkingTimeResponse;
import com.miniproject.commute.repository.member.MemberRepository;
import com.miniproject.commute.repository.workingTime.WorkingTimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class WorkingTimeService {
    private final MemberRepository memberRepository;
    private final WorkingTimeRepository workingTimeRepository;

    public WorkingTimeService(MemberRepository memberRepository, WorkingTimeRepository workingTimeRepository) {
        this.memberRepository = memberRepository;
        this.workingTimeRepository = workingTimeRepository;
    }

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

        isMemberExist(request.memberId());

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
                                        .build())
                        .toList();

        int sum = workingTimes.stream().mapToInt(WorkingTime::getWorkingMinutes).sum();

        return WorkingTimeResponse.builder().name(member.getName()).detail(listForWorkingTimeResponses).sum(sum).build();
    }

    private void isMemberExist(long id) {
        if(!memberRepository.existsById(id)){
            throw new IllegalArgumentException("존재하지 않는 멤버입니다.");
        }
    }
}
