package org.example.yeonghuns.config.Error.exception.annualLeave;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.BadRequestException;

/**
 * packageName    : org.example.yeonghuns.config.Error.exception
 * fileName       : AlreadyAttendanceException
 * author         : Yeong-Huns
 * date           : 2024-03-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-05        Yeong-Huns       최초 생성
 */
public class BeforeTodayException extends BadRequestException {
    public BeforeTodayException() {
        super(ErrorCode.REQUEST_BEFORE_TODAY);
    }
}
