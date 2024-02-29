package inflearn.mini.team.exception;

public class TeamAlreadyExistException extends RuntimeException {

    public TeamAlreadyExistException(final String message) {
        super(message);
    }
}
