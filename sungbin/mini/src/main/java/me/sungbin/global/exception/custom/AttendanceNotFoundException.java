package me.sungbin.global.exception.custom;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception.custom
 * @fileName : AttendanceNotFoundException
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */
public class AttendanceNotFoundException extends RuntimeException {
    public AttendanceNotFoundException() {
        super();
    }

    public AttendanceNotFoundException(String message) {
        super(message);
    }
}
