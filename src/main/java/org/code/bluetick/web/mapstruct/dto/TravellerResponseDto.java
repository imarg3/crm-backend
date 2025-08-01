package org.code.bluetick.web.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.code.bluetick.enums.PersonType;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravellerResponseDto {
    private Long id;
    private String name;
    private short age;
    private PersonType personType;
} 