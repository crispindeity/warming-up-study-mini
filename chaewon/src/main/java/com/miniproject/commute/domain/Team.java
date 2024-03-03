package com.miniproject.commute.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;
@Getter
@Entity
public class Team {
    protected Team() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();
    @Builder
    public Team(Long id, String name, List<Member> members) {
        this.name = name;
        if(members!=null){
            this.members=members;
        }
    }
}
