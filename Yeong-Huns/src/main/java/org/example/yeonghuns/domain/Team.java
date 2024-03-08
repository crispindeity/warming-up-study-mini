package org.example.yeonghuns.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String manager;

    private int dayBeforeAnnual;

    @OneToMany(mappedBy = "team")
    private List<Member> memberList = new ArrayList<>();

    @Builder
    public Team(String name, int dayBeforeAnnual) {
        this.name = name;
        this.dayBeforeAnnual = dayBeforeAnnual;
    }

    public int getMemberCount(){
        return memberList.size();
    }

    public void updateManager(String manager) {
        this.manager = manager;
    }

    public void updateDayBeforeAnnual(int dayBeforeAnnual){ this.dayBeforeAnnual = dayBeforeAnnual; }
}
