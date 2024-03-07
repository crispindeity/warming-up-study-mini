package org.example.yeonghuns.config.Error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String Message;
    private String code;

    public ErrorResponse(final ErrorCode code) {
        this.Message = code.getMessage();
        this.code = code.getCode();
    }
    public ErrorResponse(final ErrorCode code, final String message) {
        this.Message = message;
        this.code = code.getCode();
    }
    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
    public static ErrorResponse of(final ErrorCode code, final String message) {
        return new ErrorResponse(code, message);
    }
}
