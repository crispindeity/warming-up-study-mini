package inflearn.mini.employee.exception;

import inflearn.mini.global.NotFoundException;

public class EmployeeNotFoundException extends NotFoundException {

    public EmployeeNotFoundException(final String message) {
        super(message);
    }
}
