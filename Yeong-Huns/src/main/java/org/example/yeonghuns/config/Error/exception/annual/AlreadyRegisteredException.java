package org.example.yeonghuns.config.Error.exception.annual;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.BadRequestException;

public class AlreadyRegisteredException extends BadRequestException {
    public AlreadyRegisteredException() { super(ErrorCode.ALREADY_EXISTS_ANNUAL_LEAVE); }
}
