package br.com.wacase.shared.exceptions.errors;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class NotFoundException extends RuntimeException {
    private final String message;
    public NotFoundException(final String message) {
        super(message);
        this.message = message;
    }
}
