package me.sungbin.domain.team.entity;

import jakarta.persistence.*;
import lombok.*;
import me.sungbin.global.common.entity.BaseDateTimeEntity;
import org.hibernate.annotations.Comment;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.team.entity
 * @fileName : Team
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "팀이 생성된 날짜")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false, columnDefinition = "팀 정보가 업데이트 된 날짜"))
})
public class Team extends BaseDateTimeEntity {

    @Id
    @Comment("팀 테이블 PK")
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("팀 이름")
    @Column(name = "team_name", nullable = false)
    private String name;

    @Builder
    public Team(String name) {
        this.name = name;
    }
}
