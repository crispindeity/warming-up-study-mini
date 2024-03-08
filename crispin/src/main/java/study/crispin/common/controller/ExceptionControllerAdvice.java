package study.crispin.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.crispin.common.controller.response.ExceptionResponse;
import study.crispin.common.exception.ApiRequestException;

import java.util.List;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ExceptionResponse> apiRequestException(ApiRequestException exception) {
        String exceptionMessage = exception.getMessage();
        log.info("api request exception message : {}", exceptionMessage, exception);
        return ResponseEntity.badRequest().body(ExceptionResponse.of(exceptionMessage));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> nonExpectedExceptionHandler(RuntimeException exception) {
        String exceptionMessage = exception.getMessage();
        log.info("non expected exception message : {}", exceptionMessage, exception);
        return ResponseEntity.badRequest().body(ExceptionResponse.of(exceptionMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String exceptionMessage = exception.getMessage();
        log.info("method argument not valid exception message : {}", exceptionMessage, exception);
        List<FieldError> fieldErrors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(ExceptionResponse.of(fieldErrors.get(0).getDefaultMessage()));
    }
}
