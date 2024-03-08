package study.crispin.common.exception;

public class NotFoundException extends ApiRequestException {

    public NotFoundException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
