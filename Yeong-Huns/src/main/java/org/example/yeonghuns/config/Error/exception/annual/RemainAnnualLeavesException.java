package org.example.yeonghuns.config.Error.exception.annual;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.BadRequestException;

public class RemainAnnualLeavesException extends BadRequestException {
    public RemainAnnualLeavesException() { super(ErrorCode.NOT_REMAIN_ANNUAL_LEAVE); }
}
