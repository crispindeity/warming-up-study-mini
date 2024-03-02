package me.sungbin.global.exception.custom;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception.custom
 * @fileName : AlreadyAttendanceException
 * @date : 3/2/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/2/24       rovert         최초 생성
 */
public class AlreadyAttendanceClockInException extends RuntimeException {

    public AlreadyAttendanceClockInException() {
        super();
    }

    public AlreadyAttendanceClockInException(String message) {
        super(message);
    }
}
