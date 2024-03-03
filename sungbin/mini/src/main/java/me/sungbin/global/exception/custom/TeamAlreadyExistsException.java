package me.sungbin.global.exception.custom;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception.custom
 * @fileName : TeamAlreadyExistsException
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public class TeamAlreadyExistsException extends RuntimeException {

    public TeamAlreadyExistsException() {
        super();
    }

    public TeamAlreadyExistsException(String message) {
        super(message);
    }

    public TeamAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
