package org.example.yeonghuns.config.Error.exception.commute;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.NotFoundException;

public class AbsentCheckOutException extends NotFoundException {
    public AbsentCheckOutException() {
        super(ErrorCode.CHECKOUT_NOT_PERFORMED);
    }
}
