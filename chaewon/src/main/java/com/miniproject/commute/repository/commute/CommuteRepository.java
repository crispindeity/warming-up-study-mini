package com.miniproject.commute.repository.commute;

import com.miniproject.commute.domain.Commute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CommuteRepository extends JpaRepository<Commute, Long> {
    Commute findByMember_Id(long id);
    boolean existsByMember_IdAndWorkInBetween(long id, LocalDateTime dayStart, LocalDateTime dayEnd);
    boolean existsByMember_IdAndWorkOutBetween(long id, LocalDateTime dayStart, LocalDateTime dayEnd);
}
