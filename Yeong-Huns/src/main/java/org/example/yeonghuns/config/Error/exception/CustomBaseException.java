package org.example.yeonghuns.config.Error.exception;

import org.example.yeonghuns.config.Error.ErrorCode;

public class CustomBaseException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomBaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public CustomBaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
