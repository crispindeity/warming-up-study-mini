package inflearn.mini.workHub.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponse(e.getErrorCode());
    }
}
