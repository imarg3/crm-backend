package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    @JsonProperty("email")
    private String email;

    @JsonProperty("mobile")
    private String mobile;
}
