package org.code.bluetick.web.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String email;
    private Set<String> roles;
}
