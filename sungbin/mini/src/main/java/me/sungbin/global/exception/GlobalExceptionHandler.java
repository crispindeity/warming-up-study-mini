package me.sungbin.global.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import me.sungbin.global.exception.custom.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

import static me.sungbin.global.exception.GlobalExceptionCode.*;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception
 * @fileName : GlobalExceptionHandler
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Java Bean Validation 예외 핸들링
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handle MethodArgumentNotValidException");
        return new ResponseEntity<>(ExceptionResponse.of(INVALID_INPUT_VALUE, e.getBindingResult()),
                INVALID_INPUT_VALUE.getHttpStatus());
    }

    /**
     * EntityNotFound 예외 핸들링
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("handle EntityNotFoundException");
        return new ResponseEntity<>(
                ExceptionResponse.of(ENTITY_NOT_FOUND, e.getMessage()),
                ENTITY_NOT_FOUND.getHttpStatus());
    }

    /**
     * 유효하지 않은 클라이언트의 요청 값 예외 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("handle IllegalArgumentException");
        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, e.getMessage()),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    @ExceptionHandler(TeamAlreadyExistsException.class)
    protected ResponseEntity<ExceptionResponse> handleTeamAlreadyExistsException(TeamAlreadyExistsException e) {
        log.error("handle TeamAlreadyExistsException");

        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, e.getMessage()),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    @ExceptionHandler(TeamNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleTeamNotFoundException(TeamNotFoundException e) {
        log.error("handle TeamNotFoundException");

        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, "존재하는 팀이 없습니다."),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    @ExceptionHandler(AlreadyExistsManagerException.class)
    protected ResponseEntity<ExceptionResponse> handleAlreadyExistsManagerException(AlreadyExistsManagerException e) {
        log.error("handle AlreadyExistsManagerException");

        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, e.getMessage()),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
        log.error("handle EmployeeNotFoundException");

        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, "존재하는 직원이 없습니다."),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    @ExceptionHandler(AttendanceNotFoundException.class)
    protected ResponseEntity<ExceptionResponse> handleAttendanceNotFoundException(AttendanceNotFoundException e) {
        log.error("handle AttendanceNotFoundException");

        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, e.getMessage()),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<ExceptionResponse> handleDateTimeParseException(DateTimeParseException e) {
        log.error("handle DateTimeParseException");

        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, "잘못된 형식의 날짜입니다."),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    @ExceptionHandler(AlreadyAttendanceClockInException.class)
    protected ResponseEntity<ExceptionResponse> handleAlreadyAttendanceClockInException(AlreadyAttendanceClockInException e) {
        log.error("handle AlreadyAttendanceClockInException");

        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, e.getMessage()),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    @ExceptionHandler(AlreadyAttendanceClockOutException.class)
    protected ResponseEntity<ExceptionResponse> handleAlreadyAttendanceClockOutException(AlreadyAttendanceClockOutException e) {
        log.error("handle AlreadyAttendanceClockOutException");

        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, e.getMessage()),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    /**
     * 잘못된 HTTP Method 요청 예외 처리
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handle HttpRequestMethodNotSupportedException");
        return new ResponseEntity<>(
                ExceptionResponse.of(METHOD_NOT_ALLOWED),
                METHOD_NOT_ALLOWED.getHttpStatus()
        );
    }

    /**
     * 잘못된 타입 변환 예외 처리
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ExceptionResponse> handleBindException(BindException e) {
        log.error("handle BindException");
        return new ResponseEntity<>(
                ExceptionResponse.of(INVALID_INPUT_VALUE, e.getBindingResult()),
                INVALID_INPUT_VALUE.getHttpStatus()
        );
    }

    /**
     * 모든 예외를 처리
     * 웬만해서 여기까지 오면 안됨
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("handle Exception", e);
        return new ResponseEntity<>(
                ExceptionResponse.of(INTERNAL_SERVER_ERROR),
                INTERNAL_SERVER_ERROR.getHttpStatus()
        );
    }
}
