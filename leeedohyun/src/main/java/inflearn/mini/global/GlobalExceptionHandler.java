package inflearn.mini.global;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import inflearn.mini.employee.exception.AlreadyAtWorkException;
import inflearn.mini.employee.exception.AlreadyLeftException;
import inflearn.mini.employee.exception.EmployeeNotFoundException;
import inflearn.mini.team.exception.TeamAlreadyExistException;
import inflearn.mini.team.exception.TeamNotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({TeamNotFoundException.class, EmployeeNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleTeamNotFoundException(final RuntimeException e) {
        log.error("TeamNotFoundException: {}", e.getMessage());
        return ResponseEntity.status(NOT_FOUND)
                .body(new ExceptionResponse(NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler({TeamAlreadyExistException.class, AlreadyAtWorkException.class, AlreadyLeftException.class})
    public ResponseEntity<ExceptionResponse> handleTeamAlreadyExistsException(final RuntimeException e) {
        log.error("TeamAlreadyExistsException: {}", e.getMessage());
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ExceptionResponse(BAD_REQUEST.value(), e.getMessage()));
    }
}
