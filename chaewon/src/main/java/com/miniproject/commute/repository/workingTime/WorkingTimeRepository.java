package com.miniproject.commute.repository.workingTime;

import com.miniproject.commute.domain.WorkingTime;
import com.miniproject.commute.domain.WorkingTimePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, WorkingTimePK> {
    List<WorkingTime> findAllByWorkingTimePK_MemberIdAndWorkingTimePK_WorkingDateBetween(long memberId, LocalDate monthStartDay, LocalDate monthEndDay);

    boolean existsByWorkingTimePK_MemberIdAndWorkingTimePK_WorkingDateAndUsingDayOff(long memberId, LocalDate workingDate, boolean usingDayOff);
}
