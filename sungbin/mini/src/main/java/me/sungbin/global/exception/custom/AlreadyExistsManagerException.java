package me.sungbin.global.exception.custom;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception.custom
 * @fileName : AlreadyExistsManagerException
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public class AlreadyExistsManagerException extends RuntimeException {

    public AlreadyExistsManagerException() {
    }

    public AlreadyExistsManagerException(String message) {
        super(message);
    }

    public AlreadyExistsManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
