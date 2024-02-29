package inflearn.mini.global;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import inflearn.mini.team.exception.TeamAlreadyExistException;
import inflearn.mini.team.exception.TeamNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Error> handleTeamNotFoundException(final TeamNotFoundException e) {
        log.error("TeamNotFoundException: {}", e.getMessage());
        return ResponseEntity.status(NOT_FOUND)
                .body(new Error(NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(TeamAlreadyExistException.class)
    public ResponseEntity<Error> handleTeamAlreadyExistsException(final TeamAlreadyExistException e) {
        log.error("TeamAlreadyExistsException: {}", e.getMessage());
        return ResponseEntity.status(BAD_REQUEST)
                .body(new Error(BAD_REQUEST.value(), e.getMessage()));
    }
}
