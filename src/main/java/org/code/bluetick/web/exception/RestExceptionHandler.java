package org.code.bluetick.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        log.error("Bad request exception: {}", ex.getMessage());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
        return handleExceptionInternal(ex, error, null, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { UserAlreadyExistException.class })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistException ex, WebRequest request) {
        log.error("User already exists: {}", ex.getMessage());
        ApiError error = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), request.getDescription(false));
        return handleExceptionInternal(ex, error, null, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { UserNotFoundException.class, CustomerNotFoundException.class, LeadNotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        log.error("Resource not found: {}", ex.getMessage());
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
        return handleExceptionInternal(ex, error, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { LeadAlreadyExistException.class })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handleLeadAlreadyExistsException(LeadAlreadyExistException ex, WebRequest request) {
        log.error("Lead already exists: {}", ex.getMessage());
        ApiError error = new ApiError(HttpStatus.CONFLICT, ex.getMessage(), request.getDescription(false));
        return handleExceptionInternal(ex, error, null, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { AuthenticationException.class, BadCredentialsException.class })
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.error("Authentication failed: {}", ex.getMessage());
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "Authentication failed", request.getDescription(false));
        return handleExceptionInternal(ex, error, null, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.error("Access denied: {}", ex.getMessage());
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, "Access denied", request.getDescription(false));
        return handleExceptionInternal(ex, error, null, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = { SendMailException.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleMailException(SendMailException ex, WebRequest request) {
        log.error("Mail sending failed: {}", ex.getMessage());
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Mail service temporarily unavailable", request.getDescription(false));
        return handleExceptionInternal(ex, error, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request.getDescription(false));
        return handleExceptionInternal(ex, error, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage)
                .toList();
        log.error("Validation failed: {}", errors);
        ApiError errorDetails = new ApiError(HttpStatus.BAD_REQUEST, "Validation failed", request.getDescription(false), errors);
        return handleExceptionInternal(ex, errorDetails, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if(HttpStatus.INTERNAL_SERVER_ERROR.equals(statusCode)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        return new ResponseEntity<>(body, headers, statusCode);
    }
}
