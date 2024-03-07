package org.example.yeonghuns.config.Error;

import lombok.extern.slf4j.Slf4j;
import org.example.yeonghuns.config.Error.exception.CustomBaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException e){
        log.error("HttpRequestMethodNotSupportedException", e);
        return createErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CustomBaseException.class)
    protected ResponseEntity<ErrorResponse> handle(CustomBaseException e){
        log.error("CustomBaseException", e);
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e){
        e.getStackTrace();
        log.error("MethodArgumentNotValidException", e);
        return createErrorResponse(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e){
        e.getStackTrace();
        log.error("HttpMessageNotReadableException", e);
        return createErrorResponse(ErrorCode.MESSAGE_NOT_READABLE);
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handle(Exception e){
        e.getStackTrace();
        log.error("Exception", e);
        return createErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode){
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode),
                errorCode.getStatus());
    }
}
