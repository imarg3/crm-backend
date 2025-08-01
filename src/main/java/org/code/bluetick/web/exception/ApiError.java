package org.code.bluetick.web.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private int statusCode;
    private String status;
    private String message;
    private String description;
    private String path;
    private List<String> validationErrors;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(final HttpStatus status, final String message, String description) {
        this.statusCode = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        this.description = description;
    }

    public ApiError(final HttpStatus status, final String message, String description, List<String> validationErrors) {
        this.statusCode = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        this.description = description;
        this.validationErrors = validationErrors;
    }

    public ApiError(final HttpStatus status, final String message, String description, String path) {
        this.statusCode = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        this.description = description;
        this.path = path;
    }
}
