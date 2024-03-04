package inflearn.mini.employee.exception;

public class AlreadyLeftException extends RuntimeException {

    public AlreadyLeftException(String message) {
        super(message);
    }
}
