package inflearn.mini.employee.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(final String message) {
        super(message);
    }
}
