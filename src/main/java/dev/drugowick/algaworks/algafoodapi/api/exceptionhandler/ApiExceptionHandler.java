package dev.drugowick.algaworks.algafoodapi.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handler(EntityNotFoundException exception, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorType apiErrorType = ApiErrorType.ENTITY_NOT_FOUND;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityBeingUsedException.class)
    public ResponseEntity<?> handler(EntityBeingUsedException exception, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ApiErrorType apiErrorType = ApiErrorType.ENTITY_BEING_USED;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<?> handler(GenericBusinessException exception, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorType apiErrorType = ApiErrorType.BUSINESS_EXCEPTION;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        ApiErrorType apiErrorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = "Invalid request body. Check the syntax and properties of your request body" +
                ".";

        ApiError problem = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));

        ApiErrorType apiErrorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = String.format("The property '%s' with value '%s', "
                        + "in invalid. The value must be compatible with the type %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).build();

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    /**
     * Customizes Spring's handleExceptionInternal to create a default body for all possible exceptions already
     * handled by ResponseEntityExceptionHandler class that we extend here.
     *
     * @param ex      The exception, that won't be touched here..
     * @param body    The HTTP response body, that will be defined here as being or ApiError class.
     * @param headers The HTTP response headers, that won't be touched here.
     * @param status  The HTTP status, that won't be touched here.
     * @param request The HTTP request, that won't be touched here.
     * @return call Spring's handleExceptionInternal.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ApiError.builder()
                    .timestamp(LocalDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * A helper method that returns an ApiErrorBuilder with the default values set primary via an ENUM ApiErrorType.
     * <p>
     * This allows for any additional customization/field to used together wit the builder.
     *
     * @param status       the HTTP Status.
     * @param apiErrorType the ENUM with the error type information (defines type and title).
     * @param detail       the detailed message for the error.
     * @return an ApiError.ApiErrorBuilder to be further customized with other property values.
     */
    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType apiErrorType, String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }
}
