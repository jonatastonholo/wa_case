package br.com.wacase.shared.exceptions.errors;

import lombok.Getter;

@Getter
public final class UnauthorizedException extends ApiException {

    public UnauthorizedException(final String message) {
        super(message);
    }

    public UnauthorizedException(final Throwable throwable) {
        super(throwable);
    }

    public UnauthorizedException(String message, Throwable throwable) {
        super(throwable);
    }

    public UnauthorizedException() {
        this("Acesso negado");
    }
}
