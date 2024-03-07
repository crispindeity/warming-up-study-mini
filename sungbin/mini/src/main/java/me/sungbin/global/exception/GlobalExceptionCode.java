package me.sungbin.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception
 * @fileName : GlobalExceptionCode
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Getter
@RequiredArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode {

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "G-001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "G-002", "Invalid Http Request Method"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "G-003", "Resource Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "F-001", "Server Error!");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;
}
