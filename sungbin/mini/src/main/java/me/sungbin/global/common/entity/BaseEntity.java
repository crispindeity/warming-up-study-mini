package me.sungbin.global.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.common.entity
 * @fileName : BaseEntity
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Getter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity extends BaseDateTimeEntity {

    @CreatedBy
    @Comment("생성한 직원")
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Comment("최종 수정한 직원")
    @Column(nullable = false)
    private String updatedBy;
}
