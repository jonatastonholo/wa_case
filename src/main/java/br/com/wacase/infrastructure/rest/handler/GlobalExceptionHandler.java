package br.com.wacase.infrastructure.rest.handler;


import static br.com.wacase.infrastructure.rest.handler.ApiExceptionResponse.badRequestApiExceptionResponse;
import static br.com.wacase.infrastructure.rest.handler.ApiExceptionResponse.internalServerError;
import static br.com.wacase.infrastructure.rest.handler.ApiExceptionResponse.notFoundApiExceptionResponse;
import static br.com.wacase.infrastructure.rest.handler.ApiExceptionResponse.unauthorizedApiExceptionResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import br.com.wacase.shared.exceptions.errors.ApiException;
import br.com.wacase.shared.exceptions.errors.NoContentException;
import br.com.wacase.shared.exceptions.errors.NotFoundException;
import br.com.wacase.shared.exceptions.errors.UnauthorizedException;
import br.com.wacase.shared.exceptions.errors.ValidationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.codec.CodecException;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Order(-2)
@Slf4j
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(
            final ErrorAttributes errorAttributes,
            final WebProperties.Resources resources,
            final ApplicationContext applicationContext,
            final ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions
                .route(RequestPredicates.all(),
                        request ->
                                Mono.just(request)
                                    .publishOn(Schedulers.boundedElastic())
                                    .map(errorAttributes::getError)
                                    .flatMap(throwable -> formatErrorResponse(request, throwable)));
    }

    private Mono<ServerResponse> formatErrorResponse(
            final ServerRequest request,
            final Throwable throwable) {

        return Mono.fromCallable(() ->
        switch (throwable) {
            case ApiException e -> handleApiException(e);
            case ServerWebInputException e -> handleServerWebInputException(e);
            case UnsupportedOperationException __ -> endpointNotImplemented(request);
            case ResponseStatusException __ -> endpointNotImplemented(request);
            default -> handleDefault(throwable);
        })
        .flatMap(response -> ServerResponse
                .status(response.statusCode())
                .contentType(APPLICATION_JSON)
                .bodyValue(response));
    }

    private ApiExceptionResponse handleApiException(final ApiException apiException) {
        return switch (apiException) {
            case NotFoundException e -> handleNotFoundException(e);
            case NoContentException e -> handleNoContentException(e);
            case ValidationException e -> handleValidationException(e);
            case UnauthorizedException e -> handleUnauthorizedException(e);
            default -> new ApiExceptionResponse(400, apiException.message());
        };
    }

    private ApiExceptionResponse handleNoContentException(final NoContentException e) {
        if (StringUtils.hasText(e.message())) {
            log.error(e.message());
        }
        return new ApiExceptionResponse(NO_CONTENT.value(), e.getMessage());
    }

    private ApiExceptionResponse handleUnauthorizedException(final UnauthorizedException unauthorizedException) {
        return unauthorizedApiExceptionResponse("Acesso negado");
    }

    private ApiExceptionResponse handleServerWebInputException(final ServerWebInputException serverWebInputException) {
        if (serverWebInputException.getCause() instanceof CodecException codecException) {
            return handleCodecExeption(codecException);
        }

        if (BAD_REQUEST.equals(serverWebInputException.getStatus())) {
            return badRequestApiExceptionResponse(serverWebInputException.getMessage());
        }
        return internalServerError(serverWebInputException.getMessage(), serverWebInputException);
    }
    
    private ApiExceptionResponse handleValidationException(final ValidationException validationException) {
        return badRequestApiExceptionResponse(validationException.getMessage());
    }

    
    private ApiExceptionResponse handleNotFoundException(final NotFoundException notFoundException) {
        return notFoundApiExceptionResponse(notFoundException.getMessage());
    }

    private ApiExceptionResponse handleDefault(final Throwable throwable) {
        if (throwable.getCause() == null) {
            return internalServerError("Ocorreu um erro não mapeado", throwable);
        }
        if (throwable.getCause() instanceof CodecException codecException) {
            return handleCodecExeption(codecException);
        }
        return internalServerError("Ocorreu um erro não mapeado", throwable);
    }

    private ApiExceptionResponse handleCodecExeption(final CodecException codecException) {
        return switch (codecException.getCause()) {
            case JsonMappingException jsonMappingException
                    -> badRequestApiExceptionResponse(jsonMappingException);
            case JsonParseException jsonParseException
                    -> badRequestApiExceptionResponse(jsonParseException);
            default
                    -> internalServerError("Ocorreu um erro de decodificação não identificado.", codecException);
        };
    }
    
    private ApiExceptionResponse endpointNotImplemented(final ServerRequest request) {
        var msg = "Endpoint [" + request.method() +":"+ request.path() + "] não implementado.";
        log.debug(msg);
        return new ApiExceptionResponse(NOT_IMPLEMENTED.value(), msg);
    }

}
