package br.com.wacase.shared.exceptions.errors;

import lombok.Value;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Value
public class ValidationException extends RuntimeException {
    String message;

    public ValidationException(final String message) {
        super(message);
        this.message = message;
    }
}
