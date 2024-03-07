package me.sungbin.domain.annual.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.domain.annual.entity.AnnualLeave;
import me.sungbin.domain.annual.model.request.AnnualLeaveRequestDto;
import me.sungbin.domain.annual.model.response.AnnualLeaveResponseDto;
import me.sungbin.domain.annual.repository.AnnualLeaveRepository;
import me.sungbin.domain.employee.entity.Employee;
import me.sungbin.domain.employee.repository.EmployeeRepository;
import me.sungbin.global.exception.custom.EmployeeNotFoundException;
import me.sungbin.global.exception.custom.AnnualLeaveException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.annual.service
 * @fileName : AnnualLeaveService
 * @date : 3/4/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/4/24       rovert         최초 생성
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnualLeaveService {

    private final AnnualLeaveRepository annualLeaveRepository;

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void applyAnnualLeave(AnnualLeaveRequestDto requestDto) {
        // 요청한 직원 id가 존재하는지 여부 확인
        Employee employee = this.employeeRepository.findById(requestDto.employeeId()).orElseThrow(EmployeeNotFoundException::new);
        int dayBeforeLeaveRequired = employee.getTeam().getDayBeforeLeaveRequired(); // 팀 정책

        // 연차 사용일과 현재날짜 계산
        long daysUntilLeave = ChronoUnit.DAYS.between(LocalDate.now(), requestDto.leaveDate());

        if (daysUntilLeave < dayBeforeLeaveRequired) {
            throw new AnnualLeaveException("연차 요청이 연차 전 필수 일수를 충족하지 못했습니다.");
        }

        if (this.annualLeaveRepository.existsByEmployeeAndAnnualLeaveDate(employee, requestDto.leaveDate())) {
            throw new AnnualLeaveException("이미 해당 날짜에 연차를 사용하셨습니다.");
        }

        if (employee.getRemainingAnnualLeaves() > 0) {
            employee.useAnnualLeave();

            AnnualLeave annualLeave = requestDto.toEntity(employee);
            this.annualLeaveRepository.save(annualLeave);
        } else {
            throw new AnnualLeaveException("남아 있는 연차가 없습니다.");
        }
    }

    public AnnualLeaveResponseDto confirmRemainedAnnualLeave(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);

        return new AnnualLeaveResponseDto(employee.getRemainingAnnualLeaves());
    }
}
