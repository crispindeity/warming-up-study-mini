package inflearn.mini.employee.exception;

import inflearn.mini.global.BadRequestException;

public class AlreadyAtWorkException extends BadRequestException {

    public AlreadyAtWorkException(String message) {
        super(message);
    }
}
