package org.example.yeonghuns.config.Error.exception;

import org.example.yeonghuns.config.Error.ErrorCode;

public class MemberNotFoundException extends NotFoundException{
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
