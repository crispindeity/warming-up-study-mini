package org.example.yeonghuns.service.commute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.yeonghuns.config.Error.exception.commute.AbsentCheckOutException;
import org.example.yeonghuns.config.Error.exception.commute.AlreadyAttendanceException;
import org.example.yeonghuns.config.Error.exception.commute.AlreadyDepartureException;
import org.example.yeonghuns.config.Error.exception.commute.CommuteNotFoundException;
import org.example.yeonghuns.config.Error.exception.member.MemberNotFoundException;
import org.example.yeonghuns.domain.Commute;
import org.example.yeonghuns.domain.Member;
import org.example.yeonghuns.dto.commute.request.startOfWorkRequest;
import org.example.yeonghuns.dto.commute.request.endOfWorkRequest;
import org.example.yeonghuns.dto.commute.request.GetCommuteRecordRequest;
import org.example.yeonghuns.dto.commute.response.GetCommuteDetail;
import org.example.yeonghuns.dto.commute.response.GetCommuteRecordResponse;
import org.example.yeonghuns.repository.CommuteRepository;
import org.example.yeonghuns.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

/**
 * packageName    : org.example.yeonghuns.service.commute
 * fileName       : CommuteService
 * author         : Yeong-Huns
 * date           : 2024-03-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-04        Yeong-Huns       최초 생성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CommuteService {
    private final CommuteRepository commuteRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void startOfWork(startOfWorkRequest request) {
        Member member = findMemberById(request.id());

        Commute latestCommute = findLatestCommuteByMember(member);
        if (latestCommute.isAttendance()) throw new AbsentCheckOutException(); //이전 기록 퇴근확인

        boolean isAlreadyAttendance = LocalDate.now().equals(LocalDate.from(latestCommute.getCreatedAt()));
        if (isAlreadyAttendance) throw new AlreadyAttendanceException(); //당일 출근기록 확인

        commuteRepository.save(request.toEntity(member));
    }

    @Transactional
    public void endOfWork(@RequestBody endOfWorkRequest request) {
        Member member = findMemberById(request.id());

        Commute latestCommute = findLatestCommuteByMember(member);

        if (!latestCommute.isAttendance()) throw new AlreadyDepartureException();

        latestCommute.endOfWork(); //변경감지 자동저장
    }

    @Transactional
    public GetCommuteRecordResponse GetCommuteRecord(GetCommuteRecordRequest request) {
        findMemberById(request.id());

        List<GetCommuteDetail> commuteDetailList = findCommuteListByMemberIdAndStartOfWork(request);
        Long sum = commuteDetailList.stream()
                .map(GetCommuteDetail::workingMinutes)
                .reduce(0L, Long::sum);
        //commuteDetailList에서 workingMinutes를 조회, reduce로 합을 반환
        return new GetCommuteRecordResponse(commuteDetailList, sum);
    }

    private Member findMemberById(long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    private Commute findLatestCommuteByMember(Member member) {
        return commuteRepository.findLatestCommuteByMemberId(member.getId())
                .orElseThrow(CommuteNotFoundException::new);
    }

    private List<GetCommuteDetail> findCommuteListByMemberIdAndStartOfWork(GetCommuteRecordRequest request) {
        List<Commute> commuteList =
                commuteRepository.findCommuteListByMemberIdAndStartOfWork(request.id(), request.yearMonth().getYear(), request.yearMonth().getMonth().getValue());

        if (commuteList.isEmpty()) throw new CommuteNotFoundException(); //해당범위에 통근기록 존재 X

        return commuteList.stream().map(GetCommuteDetail::from).toList(); //CommuteDetail으로 변환
    }
}
