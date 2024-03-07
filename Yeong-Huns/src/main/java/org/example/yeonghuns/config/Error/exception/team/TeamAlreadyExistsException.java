package org.example.yeonghuns.config.Error.exception.team;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.BadRequestException;
import org.example.yeonghuns.config.Error.exception.NotFoundException;

public class TeamAlreadyExistsException extends BadRequestException {
    public TeamAlreadyExistsException() {
        super(ErrorCode.TEAM_ALREADY_EXISTS);
    }
}
