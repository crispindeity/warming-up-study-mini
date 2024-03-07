package org.example.yeonghuns.config.Error.exception.annualLeave;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.BadRequestException;

public class AcceptTeamPolicyException extends BadRequestException {
    public AcceptTeamPolicyException() { super(ErrorCode.DECLINE_ANNUAL_LEAVE_REQUEST); }
}
