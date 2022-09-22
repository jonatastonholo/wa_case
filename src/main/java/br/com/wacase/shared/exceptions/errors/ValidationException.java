package br.com.wacase.shared.exceptions.errors;

public final class ValidationException extends ApiException {
    public ValidationException(final String message) {
        super(message);
    }
}
