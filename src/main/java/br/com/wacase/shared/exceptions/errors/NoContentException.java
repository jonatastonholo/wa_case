package br.com.wacase.shared.exceptions.errors;

public final class NoContentException extends ApiException {
    public NoContentException(final String message) {
        super(message);
    }
    public NoContentException() {
        super("");
    }
}
