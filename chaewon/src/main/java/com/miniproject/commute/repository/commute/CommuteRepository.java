package com.miniproject.commute.repository.commute;

import com.miniproject.commute.domain.Commute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CommuteRepository extends JpaRepository<Commute, Long> {
    public Commute findByMember_Id(long id);
    public boolean existsByMember_IdAndWorkInBetween(long id, LocalDateTime dayStart, LocalDateTime dayEnd);
    public boolean existsByMember_IdAndWorkOutBetween(long id, LocalDateTime dayStart, LocalDateTime dayEnd);
}
