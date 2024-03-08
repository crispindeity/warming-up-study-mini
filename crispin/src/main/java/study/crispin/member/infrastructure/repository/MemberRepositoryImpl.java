package study.crispin.member.infrastructure.repository;

import org.springframework.stereotype.Repository;
import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.entity.MemberEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    public MemberRepositoryImpl(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    @Override
    public Member save(Member member) {
        return jpaMemberRepository.save(MemberEntity.fromModel(member))
                .toModel();
    }

    @Override
    public Optional<Member> findByNameAndBirthdayAndWorkStartDate(
            String name,
            LocalDate birthday,
            LocalDate workStartDate
    ) {
        return jpaMemberRepository.findByNameAndBirthdayAndWorkStartDate(name, birthday, workStartDate)
                .map(memberEntity -> MemberEntity.toModel(memberEntity));
    }

    @Override
    public List<Member> findAll() {
        return jpaMemberRepository.findAll().stream()
                .map(memberEntity -> MemberEntity.toModel(memberEntity))
                .toList();
    }

    @Override
    public Long countByTeamName(String name) {
        return jpaMemberRepository.countByTeamName(name);
    }

    @Override
    public boolean existsByNameAndBirthdayAndWorkStartDate(String name, LocalDate birthday, LocalDate workStartDate) {
        return jpaMemberRepository.existsByNameAndBirthdayAndWorkStartDate(name, birthday, workStartDate);
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return jpaMemberRepository.findById(memberId)
                .map(memberEntity -> MemberEntity.toModel(memberEntity));
    }

    @Override
    public boolean existsById(Long id) {
        return jpaMemberRepository.existsById(id);
    }
}
