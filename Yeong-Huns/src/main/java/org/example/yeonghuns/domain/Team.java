package org.example.yeonghuns.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.example.yeonghuns.dto.team.response.GetAllTeamsResponse;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {

    protected Team() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String manager;

    @OneToMany(mappedBy = "team")
    private List<Member> memberList = new ArrayList<>();

    @Builder
    public Team(String name) {
        this.name = name;
    }

    public int getMemberCount(){
        return memberList.size();
    }

    public void updateManager(String manager) {
        this.manager = manager;
    }

}
