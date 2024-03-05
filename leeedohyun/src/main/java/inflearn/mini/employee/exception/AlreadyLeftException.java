package inflearn.mini.employee.exception;

import inflearn.mini.global.BadRequestException;

public class AlreadyLeftException extends BadRequestException {

    public AlreadyLeftException(String message) {
        super(message);
    }
}
