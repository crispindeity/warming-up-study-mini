package inflearn.mini.team.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException(final String message) {
        super(message);
    }
}
