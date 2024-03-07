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
    @Column(nullable = false, length = 20, unique = true)
    private String name;
    @Column(nullable = false)
    private Integer dayOffPeriod; //팀의 휴가 신청 기간
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();
    @Builder
    public Team(Long id, String name, List<Member> members, Integer dayOffPeriod) {
        this.name = name;
        if(members!=null){
            this.members=members;
        }
        if (dayOffPeriod!=null){
            this.dayOffPeriod=dayOffPeriod;
        }
    }
}
