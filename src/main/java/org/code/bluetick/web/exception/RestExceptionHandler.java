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

/**
 * Global exception handler using Java 21 pattern matching and switch expressions.
 * Centralizes error handling across all REST controllers.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles various exceptions using Java 21 pattern matching for instanceof.
     * This demonstrates modern exception handling patterns.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        // Java 21 pattern matching for instanceof with switch expression
        return switch (ex) {
            case IllegalArgumentException iae -> {
                log.error("Bad request - Illegal argument: {}", iae.getMessage());
                yield buildErrorResponse(HttpStatus.BAD_REQUEST, iae.getMessage(), request);
            }
            case IllegalStateException ise -> {
                log.error("Bad request - Illegal state: {}", ise.getMessage());
                yield buildErrorResponse(HttpStatus.BAD_REQUEST, ise.getMessage(), request);
            }
            case UserAlreadyExistException uae -> {
                log.error("User already exists: {}", uae.getMessage());
                yield buildErrorResponse(HttpStatus.CONFLICT, uae.getMessage(), request);
            }
            case LeadAlreadyExistException lae -> {
                log.error("Lead already exists: {}", lae.getMessage());
                yield buildErrorResponse(HttpStatus.CONFLICT, lae.getMessage(), request);
            }
            case UserNotFoundException unf -> {
                log.error("User not found: {}", unf.getMessage());
                yield buildErrorResponse(HttpStatus.NOT_FOUND, unf.getMessage(), request);
            }
            case CustomerNotFoundException cnf -> {
                log.error("Customer not found: {}", cnf.getMessage());
                yield buildErrorResponse(HttpStatus.NOT_FOUND, cnf.getMessage(), request);
            }
            case LeadNotFoundException lnf -> {
                log.error("Lead not found: {}", lnf.getMessage());
                yield buildErrorResponse(HttpStatus.NOT_FOUND, lnf.getMessage(), request);
            }
            case BadCredentialsException bce -> {
                log.error("Bad credentials: {}", bce.getMessage());
                yield buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", request);
            }
            case AuthenticationException ae -> {
                log.error("Authentication failed: {}", ae.getMessage());
                yield buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed", request);
            }
            case AccessDeniedException ade -> {
                log.error("Access denied: {}", ade.getMessage());
                yield buildErrorResponse(HttpStatus.FORBIDDEN, "Access denied", request);
            }
            case SendMailException sme -> {
                log.error("Mail sending failed: {}", sme.getMessage());
                yield buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Mail service temporarily unavailable", request);
            }
            default -> {
                log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
                yield buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred", request);
            }
        };
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        log.error("Validation failed: {}", errors);
        ApiError errorDetails = new ApiError(
            HttpStatus.BAD_REQUEST,
            "Validation failed",
            request.getDescription(false),
            errors
        );
        return handleExceptionInternal(ex, errorDetails, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(statusCode)) {
            request.setAttribute("jakarta.servlet.error.exception", ex, 0);
        }
        return new ResponseEntity<>(body, headers, statusCode);
    }

    /**
     * Helper method to build consistent error responses.
     * Uses modern Java features for cleaner code.
     */
    private ResponseEntity<Object> buildErrorResponse(
            HttpStatus status,
            String message,
            WebRequest request) {
        ApiError error = new ApiError(status, message, request.getDescription(false));
        return new ResponseEntity<>(error, status);
    }
}
