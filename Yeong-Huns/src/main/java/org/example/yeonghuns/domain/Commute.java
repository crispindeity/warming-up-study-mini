package org.example.yeonghuns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.yeonghuns.common.BaseEntity;

/**
 * packageName    : org.example.yeonghuns.domain
 * fileName       : Commute
 * author         : Yeong-Huns
 * date           : 2024-03-04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-04        Yeong-Huns       최초 생성
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name= "createdAt", column = @Column(name= "start_of_work")),
        @AttributeOverride(name= "updatedAt", column = @Column(name= "end_of_work"))
})
public class Commute extends BaseEntity { //BaseEntity 상속받음

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean attendance = true; //출근 상태

    @ManyToOne(fetch= FetchType.LAZY)
    private Member member;

    public void endOfWork(){
        this.attendance = false;
    }

    @Builder
    public Commute(Member member){
        this.member = member;
    }

}

