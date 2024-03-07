package org.example.yeonghuns.config.Error.exception;

import org.example.yeonghuns.config.Error.ErrorCode;
import org.example.yeonghuns.config.Error.exception.CustomBaseException;

public class BadRequestException extends CustomBaseException {
    public BadRequestException(ErrorCode errorCode){
        super(errorCode.getMessage(), errorCode);
    }
    public BadRequestException(){
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
