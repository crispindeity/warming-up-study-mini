package org.example.yeonghuns.config.Error.exception;

import org.example.yeonghuns.config.Error.ErrorCode;

public class TeamNotFoundException extends NotFoundException{
    public TeamNotFoundException() {
        super(ErrorCode.TEAM_NOT_FOUND);
    }
}
