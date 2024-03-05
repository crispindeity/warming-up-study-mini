package inflearn.mini.team.exception;

import inflearn.mini.global.NotFoundException;

public class TeamNotFoundException extends NotFoundException {

    public TeamNotFoundException(final String message) {
        super(message);
    }
}
