package br.com.wacase.shared.exceptions.errors;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final String message;

    public UnauthorizedException(String message) {
        super(message);
        this.message = message;
    }
    public UnauthorizedException(Throwable throwable) {
        super(throwable);
        this.message = "Acesso negado";
    }
    public UnauthorizedException(String message, Throwable throwable) {
        super(throwable);
        this.message = message;
    }

    public UnauthorizedException() {
        this("Acesso negado");
    }
}
