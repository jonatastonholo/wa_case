package br.com.wacase.shared.exceptions.handler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
record ApiExceptionResponse(int statusCode, String message) {
    private static final String INTERNAL_ERROR_MESSAGE = "Ops... Something went wrong. Try again later.";
    public static ApiExceptionResponse internalServerError(final String message, final Throwable throwable) {
        log.error(message, throwable);
        return new ApiExceptionResponse(500, INTERNAL_ERROR_MESSAGE);
    }

    public static ApiExceptionResponse badRequestApiExceptionResponse(final Throwable throwable) {
        log.error(throwable.getMessage());
        return new ApiExceptionResponse(400, throwable.getMessage());
    }

    public static ApiExceptionResponse badRequestApiExceptionResponse(final String message) {
        log.error(message);
        return new ApiExceptionResponse(400, message);
    }

    public static ApiExceptionResponse notFoundApiExceptionResponse(final String message) {
        log.error(message);
        return new ApiExceptionResponse(404, message);
    }

    public static ApiExceptionResponse unauthorizedApiExceptionResponse(final String message) {
        log.error(message);
        return new ApiExceptionResponse(401, message);
    }
}
