package study.crispin.mock;

import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.repository.MemberRepository;

import java.time.LocalDate;
import java.util.*;

public class FakeMemberRepository implements MemberRepository {

    private final Map<Long, Member> storage = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Member save(Member member) {
        if (member.id() == null || storage.get(member.id()) == null) {
            Member newMember = Member.of(
                    ++sequence,
                    member.name(),
                    member.teamName(),
                    member.birthday(),
                    member.workStartDate()
            );
            storage.put(sequence, newMember);
            return storage.get(sequence);
        } else {
            storage.put(sequence, member);
            return storage.get(member.id());
        }
    }

    @Override
    public Optional<Member> findByNameAndBirthdayAndWorkStartDate(
            String name,
            LocalDate birthday,
            LocalDate workStartDate
    ) {
        return storage.values().stream()
                .filter(member -> member.isDuplicateMember(member))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Long countByTeamName(String teamName) {
        return storage.values().stream()
                .filter(member -> member.isTeamMember(teamName))
                .count();
    }

    @Override
    public boolean existsByNameAndBirthdayAndWorkStartDate(String name, LocalDate birthday, LocalDate workStartDate) {
        return storage.values()
                .stream()
                .anyMatch(member -> member.isEqualMember(name, birthday, workStartDate));
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return storage.values()
                .stream()
                .filter(member -> member.isMatchId(memberId))
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return storage.values()
                .stream()
                .anyMatch(member -> member.isMatchId(id));
    }
}
