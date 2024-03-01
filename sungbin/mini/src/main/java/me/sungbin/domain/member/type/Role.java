package me.sungbin.domain.member.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.sungbin.global.common.type.EnumType;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.member.type
 * @fileName : Role
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Getter
@RequiredArgsConstructor
public enum Role implements EnumType {
    MEMBER("MEMBER", "팀원"),
    MANAGER("MANAGER", "매니저");

    private final String name;

    private final String description;
}
