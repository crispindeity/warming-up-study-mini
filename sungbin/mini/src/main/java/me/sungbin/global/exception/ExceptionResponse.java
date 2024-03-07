package me.sungbin.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception
 * @fileName : ExceptionResponse
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionResponse {

    private String message;

    private HttpStatus status;

    private String code;

    private List<ValidationException> errors;

    private LocalDateTime timestamp;

    private ExceptionResponse(final ExceptionCode exceptionCode) {
        this.message = exceptionCode.getMessage();
        this.status = exceptionCode.getHttpStatus();
        this.code = exceptionCode.getCode();
        this.timestamp = LocalDateTime.now();
        this.errors = new ArrayList<>();
    }

    private ExceptionResponse(final ExceptionCode errorCode, final String message) {
        this.message = message;
        this.status = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
        this.timestamp = LocalDateTime.now();
        this.errors = new ArrayList<>();
    }

    private ExceptionResponse(final ExceptionCode errorCode, final List<ValidationException> errors) {
        this.message = errorCode.getMessage();
        this.status = errorCode.getHttpStatus();
        this.code = errorCode.getCode();
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public static ExceptionResponse of(final ExceptionCode errorCode) {
        return new ExceptionResponse(errorCode);
    }
    public static ExceptionResponse of(final ExceptionCode errorCode, final String message) {
        return new ExceptionResponse(errorCode, message);
    }

    public static ExceptionResponse of(final ExceptionCode code, final BindingResult bindingResult) {
        return new ExceptionResponse(code, ValidationException.of(bindingResult));
    }
    public static ExceptionResponse of(final ExceptionCode errorCode, final List<ValidationException> errors) {
        return new ExceptionResponse(errorCode, errors);
    }
}
