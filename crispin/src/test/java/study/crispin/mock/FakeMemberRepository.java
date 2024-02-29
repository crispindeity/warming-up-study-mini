package study.crispin.mock;

import study.crispin.member.domain.Member;
import study.crispin.member.infrastructure.repository.MemberRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public Optional<Member> findByNameAndBirthdayAndworkStartDate(
            String name,
            LocalDate birthday,
            LocalDate workStartDate
    ) {
        return storage.values().stream()
                .filter(member -> member.name().equals(name) &&
                        member.birthday().equals(birthday) &&
                        member.workStartDate().equals(workStartDate))
                .findFirst();
    }
}