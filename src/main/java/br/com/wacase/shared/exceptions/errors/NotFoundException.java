package br.com.wacase.shared.exceptions.errors;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public final class NotFoundException extends ApiException {
    public NotFoundException(final String message) {
        super(message);
    }
}
