package me.sungbin.global.exception.custom;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception.custom
 * @fileName : AlreadyClockOutException
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */
public class AlreadyAttendanceClockOutException extends RuntimeException {

    public AlreadyAttendanceClockOutException() {
    }

    public AlreadyAttendanceClockOutException(String message) {
        super(message);
    }
}
