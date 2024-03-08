package study.crispin.common.exception;

public class VerificationException extends ApiRequestException {

    public VerificationException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
