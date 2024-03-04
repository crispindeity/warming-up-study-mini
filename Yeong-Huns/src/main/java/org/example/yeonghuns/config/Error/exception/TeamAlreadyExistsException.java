package org.example.yeonghuns.config.Error.exception;

import org.example.yeonghuns.config.Error.ErrorCode;

public class TeamAlreadyExistsException extends NotFoundException{
    public TeamAlreadyExistsException() {
        super(ErrorCode.TEAM_ALREADY_EXISTS);
    }
}
