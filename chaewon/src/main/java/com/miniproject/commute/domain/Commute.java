package com.miniproject.commute.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@DynamicInsert
public class Commute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime workIn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private LocalDateTime workOut;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;
    @Builder
    public Commute(LocalDateTime workIn, LocalDateTime workOut, Member member) {
        this.workIn = workIn;
        this.workOut = workOut;
        this.member = member;
    }

    public void WorkOut() {
        this.workOut = LocalDateTime.now();
    }
}
