package inflearn.mini.team.exception;

import inflearn.mini.global.BadRequestException;

public class TeamAlreadyExistException extends BadRequestException {

    public TeamAlreadyExistException(final String message) {
        super(message);
    }
}
