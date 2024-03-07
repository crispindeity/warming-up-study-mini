package me.sungbin.global.exception.custom;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception.custom
 * @fileName : RejectedAnnualLeave
 * @date : 3/5/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/5/24       rovert         최초 생성
 */
public class AnnualLeaveException extends RuntimeException {

    public AnnualLeaveException() {
        super();
    }

    public AnnualLeaveException(String message) {
        super(message);
    }
}
