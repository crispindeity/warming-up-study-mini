package com.miniproject.commute.service.commute;

import com.miniproject.commute.domain.Commute;
import com.miniproject.commute.domain.Member;
import com.miniproject.commute.domain.WorkingTime;
import com.miniproject.commute.domain.WorkingTimePK;
import com.miniproject.commute.dto.commute.request.WorkInRequest;
import com.miniproject.commute.dto.commute.request.WorkOutRequest;
import com.miniproject.commute.repository.commute.CommuteRepository;
import com.miniproject.commute.repository.member.MemberRepository;
import com.miniproject.commute.repository.workingTime.WorkingTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommuteService {

    private final CommuteRepository commuteRepository;
    private final MemberRepository memberRepository;
    private final WorkingTimeRepository workingTimeRepository;

    /**
     * 출근 기록 저장
     * @param request
     * 1. 존재하는 멤버인지 확인
     * 2-1. 연차를 접수한 날인데 출근을 시도하려는지 확인
     * 2. 이미 출근한 멤버인지 확인
     * 3. 퇴근했는데 당일에 다시 출근하려는지 확인
     * 4. 1-3을 통과 시 commute 테이블에 저장
     */
    @Transactional
    public void workInMember(WorkInRequest request) {

        checkMemberExist(request.memberId());

        if(workingTimeRepository.existsByWorkingTimePK_MemberIdAndWorkingTimePK_WorkingDateAndUsingDayOff(request.memberId(), LocalDate.now(), true)){
            throw new IllegalArgumentException("당일은 연차 처리가 되어 있습니다.");
        }

        checkMemberAlreadyWorkIn(request.memberId());

        checkMemberAlreadyWorkOut(request.memberId());

        Member member = memberRepository.findById(request.memberId()).get();

        commuteRepository.save(Commute.builder().member(member).build());
    }

    /**
     * 퇴근 기록 저장
     * @param request
     * 1. 존재하는 멤버인지 확인
     * 2. 출근하지 않은 멤버인지 확인
     * 3. 이미 퇴근한 멤버인지 확인
     * 4. 1-3을 통과 시 commute 테이블에 저장
     * 5. 출퇴근을 완료했으므로 일한 기간(분)을 저장하는 working_times 테이블에 저장
     */
    @Transactional
    public void workOutMember(WorkOutRequest request) {

        checkMemberExist(request.memberId());

        checkMemberNotWorkIn(request.memberId());

        checkMemberAlreadyWorkOut(request.memberId());

        Commute commute = commuteRepository.findByMember_Id(request.memberId());
        commute.WorkOut();

        int workingMinute = Long.valueOf(ChronoUnit.MINUTES.between(commute.getWorkIn(), LocalDateTime.now())).intValue();

        WorkingTimePK workingTimePK = new WorkingTimePK(request.memberId(), LocalDate.now());
        workingTimeRepository.save(WorkingTime.builder().workingMinutes(workingMinute).workingTimePK(workingTimePK).build());
    }

    private LocalDateTime dayStart(){
        return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
    }

    private LocalDateTime dayEnd(){
        return LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
    }

    private void checkMemberExist(long id) {
        if(!memberRepository.existsById(id)){
            throw new IllegalArgumentException("존재하지 않는 멤버입니다.");
        }
    }
    private void checkMemberAlreadyWorkIn(long id) {
        if (commuteRepository.existsByMember_IdAndWorkInBetween(id, dayStart(), dayEnd())){
            throw new IllegalArgumentException("당일 이미 출근 기록이 있습니다.");
        }
    }
    private void checkMemberNotWorkIn(long id) {
        if (!commuteRepository.existsByMember_IdAndWorkInBetween(id, dayStart(), dayEnd())){
            throw new IllegalArgumentException("당일의 출근 기록이 없습니다.");
        }
    }

    private void checkMemberAlreadyWorkOut(long id) {
        if(commuteRepository.existsByMember_IdAndWorkOutBetween(id, dayStart(), dayEnd())){
            throw new IllegalArgumentException("이미 당일에 퇴근 기록이 존재합니다.");
        }
    }
}
