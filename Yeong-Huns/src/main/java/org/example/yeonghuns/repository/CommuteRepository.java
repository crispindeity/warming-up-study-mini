package org.example.yeonghuns.repository;

import org.example.yeonghuns.domain.Commute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : org.example.yeonghuns.repository
 * fileName       : CommuteRepository
 * author         : Yeong-Huns
 * date           : 2024-03-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-04        Yeong-Huns       최초 생성
 */
public interface CommuteRepository extends JpaRepository<Commute, Long> {
    Optional<Commute> findFirstByMemberIdOrderByCreatedAtDesc(Long memberId);

    @Query("SELECT commute FROM Commute commute WHERE commute.member.id= :memberId AND FUNCTION('YEAR', commute.createdAt)= :year AND FUNCTION('MONTH', commute.createdAt)= :month")
    List<Commute> findCommuteListByMemberIdAndStartOfWork(Long memberId, int year, int month);
}
