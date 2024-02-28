package inflearn.mini.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import inflearn.mini.team.exception.TeamNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Error> handleTeamNotFoundException(final TeamNotFoundException e) {
        log.error("TeamNotFoundException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }
}
