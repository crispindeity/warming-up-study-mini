package inflearn.mini.annualleave.exception;

import inflearn.mini.global.BadRequestException;

public class ExhaustedAnnualLeaveException extends BadRequestException {

    public ExhaustedAnnualLeaveException(String message) {
        super(message);
    }
}
