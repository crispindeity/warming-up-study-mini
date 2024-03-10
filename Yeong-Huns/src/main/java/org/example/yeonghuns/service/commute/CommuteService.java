package org.example.yeonghuns.service.commute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.yeonghuns.config.Error.exception.commute.*;
import org.example.yeonghuns.domain.AnnualLeave;
import org.example.yeonghuns.domain.Commute;
import org.example.yeonghuns.domain.Member;
import org.example.yeonghuns.dto.commute.request.EndOfWorkRequest;
import org.example.yeonghuns.dto.commute.request.GetCommuteRecordRequest;
import org.example.yeonghuns.dto.commute.request.StartOfWorkRequest;
import org.example.yeonghuns.dto.commute.response.GetCommuteDetail;
import org.example.yeonghuns.dto.commute.response.GetCommuteRecordResponse;
import org.example.yeonghuns.repository.CommuteRepository;
import org.example.yeonghuns.service.annual.AnnualLeaveService;
import org.example.yeonghuns.service.member.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final AnnualLeaveService annualLeaveService;
    private final MemberService memberService;

    @Transactional
    public void startOfWork(StartOfWorkRequest request) {
        Member member = memberService.findMemberById(request.id());
        Optional<Commute> commute = findLatestCommuteByMember(member);
        if (commute.isPresent()) {
            Commute latestCommute = commute.orElseThrow(CommuteNotFoundException::new);
            if (latestCommute.isAttendance()) throw new AbsentCheckOutException(); //이전 기록 퇴근확인
            boolean isAlreadyAttendance = LocalDate.now().equals(LocalDate.from(latestCommute.getCreatedAt()));
            if (isAlreadyAttendance) throw new AlreadyAttendanceException(); //당일 출근기록 확인
        }
        boolean isAlreadyAnnualLeaves = annualLeaveService.isAlreadyUsingAnnualLeaves(member, LocalDate.now());
        if (isAlreadyAnnualLeaves) throw new UsingAnnualLeavesException(); //인권보장
        commuteRepository.save(request.toEntity(member));
    }

    @Transactional
    public void endOfWork(EndOfWorkRequest request) {
        Member member = memberService.findMemberById(request.id());

        Commute latestCommute = findLatestCommuteByMember(member).orElseThrow(CommuteNotFoundException::new);

        if (!latestCommute.isAttendance()) throw new AlreadyDepartureException();

        latestCommute.endOfWork(); //변경감지 자동저장
    }

    @Transactional(readOnly = true)
    public GetCommuteRecordResponse GetCommuteRecord(GetCommuteRecordRequest request) {
        memberService.findMemberById(request.id());

        List<GetCommuteDetail> commuteDetailList = findCommuteListByMemberIdAndStartOfWork(request);
        long sum = commuteDetailList.stream()
                .mapToLong(GetCommuteDetail::workingMinutes)
                .sum();
        //commuteDetailList에서 workingMinutes를 조회, reduce로 합을 반환
        return new GetCommuteRecordResponse(commuteDetailList, sum);
    }


    private Optional<Commute> findLatestCommuteByMember(Member member) {
        return commuteRepository.findFirstByMemberIdOrderByCreatedAtDesc(member.getId());
    }

    private List<GetCommuteDetail> findCommuteListByMemberIdAndStartOfWork(GetCommuteRecordRequest request) {
        List<Commute> commuteList = commuteRepository
                .findCommuteListByMemberIdAndStartOfWork(request.id(), request.getYear(), request.getMonth());
        if (commuteList.isEmpty()) throw new CommuteNotFoundException();
        //해당범위에 통근기록 존재 X? -> 통근기록없음 예외처리
        List<GetCommuteDetail> commuteDetailList = commuteList.stream()
                .map(GetCommuteDetail::from)
                .collect(Collectors.toList()); //통근기록을 CommuteDetail으로 변환

        List<AnnualLeave> annualLeaveLeavesList = annualLeaveService // 연차기록찾기 (오늘보다 미래의 연차기록은 가져오지않음)
                .findAnnualLeavesByMemberIdAndYearMonth(request.id(), request.yearMonth());

        mergeAndSort(commuteDetailList, annualLeaveLeavesList); // .addAll()을 통한 merge
        return Collections.unmodifiableList(commuteDetailList); // 불변리스트로 변환 후 반환
    }

    private void mergeAndSort(List<GetCommuteDetail> commuteDetailList, List<AnnualLeave> annualLeaveLeavesList) {
        if (annualLeaveLeavesList != null) { //해당범위 연차기록이 있으면 합침
            List<GetCommuteDetail> annualLeavesToDetails = annualLeaveLeavesList.stream()
                    .map(GetCommuteDetail::from)
                    .toList();
            commuteDetailList.addAll(annualLeavesToDetails);
        }
        commuteDetailList.sort(Comparator.comparing(GetCommuteDetail::date));
    }
}
