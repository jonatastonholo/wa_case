package br.com.wacase.shared.exceptions.errors;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public sealed class ApiException extends RuntimeException permits NoContentException,
        NotFoundException, UnauthorizedException, ValidationException {
    private final String message;

    public ApiException(final String message) {
        super(message);
        this.message = message;
    }

    public ApiException(Throwable throwable) {
        this(throwable.getMessage());
    }
}
