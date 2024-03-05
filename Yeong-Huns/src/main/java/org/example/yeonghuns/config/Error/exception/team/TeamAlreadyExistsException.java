package org.example.yeonghuns.config.Error.exception.team;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.NotFoundException;

public class TeamAlreadyExistsException extends NotFoundException {
    public TeamAlreadyExistsException() {
        super(ErrorCode.TEAM_ALREADY_EXISTS);
    }
}
