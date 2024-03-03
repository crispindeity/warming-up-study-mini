package me.sungbin.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.exception
 * @fileName : ValidationException
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValidationException {

    private String field;
    private String value;
    private String reason;

    private ValidationException(String field, String value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    public static List<ValidationException> of(final String field, final String value, final String reason) {
        List<ValidationException> validationExceptions = new ArrayList<>();
        validationExceptions.add(new ValidationException(field, value, reason));
        return validationExceptions;
    }

    public static List<ValidationException> of(final BindingResult bindingResult) {
        final List<FieldError> validationExceptions = bindingResult.getFieldErrors();
        return validationExceptions.stream()
                .map(error -> new ValidationException(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
