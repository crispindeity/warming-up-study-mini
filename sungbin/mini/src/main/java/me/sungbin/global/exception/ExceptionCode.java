package me.sungbin.global.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception
 * @fileName : ExceptionCode
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public interface ExceptionCode {
    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();
}
