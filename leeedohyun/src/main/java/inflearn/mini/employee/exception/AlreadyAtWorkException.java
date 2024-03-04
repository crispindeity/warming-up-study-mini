package inflearn.mini.employee.exception;

public class AlreadyAtWorkException extends RuntimeException {

    public AlreadyAtWorkException(String message) {
        super(message);
    }
}
