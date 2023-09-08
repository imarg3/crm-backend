package org.code.bluetick.web.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class GenericResponse {
    private String message;
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public GenericResponse(final String message, final HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
