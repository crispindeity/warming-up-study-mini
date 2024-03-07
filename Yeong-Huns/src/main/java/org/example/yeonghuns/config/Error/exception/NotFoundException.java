package org.example.yeonghuns.config.Error.exception;

import org.example.yeonghuns.config.Error.ErrorCode;

public class NotFoundException extends CustomBaseException{
    public NotFoundException(ErrorCode errorCode){
        super(errorCode.getMessage(), errorCode);
    }
    public NotFoundException(){
        super(ErrorCode.NOT_FOUND);
    }
}
