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

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(final HttpStatus status, final String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
    }
}
