package org.example.yeonghuns.repository;

import org.example.yeonghuns.domain.AnnualLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * packageName    : org.example.yeonghuns.repository
 * fileName       : AnnualRepository
 * author         : Yeong-Huns
 * date           : 2024-03-06
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-06        Yeong-Huns       최초 생성
 */
public interface AnnualLeaveRepository extends JpaRepository<AnnualLeave, Long> {
    boolean existsByMemberIdAndAnnualDateLeaveEquals(long memberId, LocalDate annualDate);
    @Query("SELECT COUNT(*) FROM AnnualLeave annual " +
            "WHERE annual.member.id = :memberId " +
            "AND FUNCTION('YEAR', annual.annualDateLeave) = :year")
    long countByMemberId(long memberId, int year);

    @Query("SELECT annual FROM AnnualLeave annual " +
            "WHERE annual.member.id = :memberId " +
            "AND FUNCTION('YEAR', annual.annualDateLeave) = :year " +
            "AND FUNCTION('MONTH', annual.annualDateLeave) = :month " +
            "AND annual.annualDateLeave <= CURRENT_DATE()")
    List<AnnualLeave> findAllAnnualLeavesByMemberIdAndYearMonth(long memberId, int year, int month);
}
