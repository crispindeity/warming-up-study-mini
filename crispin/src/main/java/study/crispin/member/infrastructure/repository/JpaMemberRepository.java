package study.crispin.member.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.crispin.member.infrastructure.entity.MemberEntity;

import java.time.LocalDate;
import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByNameAndBirthdayAndWorkStartDate(String name, LocalDate birthday, LocalDate workStartDate);

    Long countByTeamName(String name);
}
