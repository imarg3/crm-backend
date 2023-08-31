package org.code.bluetick.web.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApiError {
    private HttpStatus status;

    private String message;

    private List<String> errors;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public ApiError(final HttpStatus status, final String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(final HttpStatus status, final Throwable ex) {
        this.status = status;
        this.message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
    }
}
