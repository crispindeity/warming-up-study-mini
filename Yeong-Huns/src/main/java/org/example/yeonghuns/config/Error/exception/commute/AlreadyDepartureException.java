package org.example.yeonghuns.config.Error.exception.commute;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.NotFoundException;

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
public class AlreadyDepartureException extends NotFoundException {
    public AlreadyDepartureException() {
        super(ErrorCode.IS_ALREADY_DEPARTURE);
    }
}
