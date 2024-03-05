package inflearn.mini.employee.exception;

import inflearn.mini.global.BadRequestException;

public class AbsentEmployeeException extends BadRequestException {

    public AbsentEmployeeException(String message) {
        super(message);
    }
}
