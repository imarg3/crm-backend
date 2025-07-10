package org.code.bluetick.web.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {
    private boolean success;
    private String message;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    public static <T> GenericResponse<T> empty() {
        return success("success!", null);
    }

    public static <T> GenericResponse<T> success(String message, T data) {
        return GenericResponse.<T>builder()
                .message(message)
                .data(data)
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> GenericResponse<T> error(String message) {
        return GenericResponse.<T>builder()
                .message(message)
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
