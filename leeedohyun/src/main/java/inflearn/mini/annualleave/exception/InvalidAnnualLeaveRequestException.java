package inflearn.mini.annualleave.exception;

import inflearn.mini.global.BadRequestException;

public class InvalidAnnualLeaveRequestException extends BadRequestException {

    public InvalidAnnualLeaveRequestException(final String message) {
        super(message);
    }
}
