package org.code.bluetick.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.persistence.model.User;
import org.code.bluetick.registration.OnRegistrationCompleteEvent;
import org.code.bluetick.service.UserDetailsImpl;
import org.code.bluetick.service.UserService;
import org.code.bluetick.web.mapstruct.dto.LoginDto;
import org.code.bluetick.web.mapstruct.dto.UserDto;
import org.code.bluetick.web.mapstruct.dto.UserResponseDto;
import org.code.bluetick.web.mapstruct.mapper.MapStructMapper;
import org.code.bluetick.web.payload.GenericResponse;
import org.code.bluetick.security.JwtUtils;
import org.code.bluetick.web.payload.JwtResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@Slf4j
@AllArgsConstructor
@Tag(name = "Authentication", description = "Authentication and authorization operations")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final MapStructMapper mapStructMapper;

    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    private final JwtUtils jwtUtils;

    @Operation(summary = "Register a new user account", 
               description = "Creates a new user account with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class))),
        @ApiResponse(responseCode = "409", description = "User already exists",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @PostMapping(value = "/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<UserResponseDto>> registerUserAccount(@Valid @RequestBody final UserDto userDto, final HttpServletRequest request) {
        log.info("Registering user account with email: {}", userDto.getEmail());

        final User registered = userService.registerNewUserAccount(mapStructMapper.userDtoToUser(userDto));
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));

        // Map User to UserResponseDto
        UserResponseDto responseDto = mapStructMapper.userToUserResponseDto(registered);

        return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse
                .success("User registered successfully", responseDto));
    }

    @Operation(summary = "Authenticate user", 
               description = "Authenticates a user with email/username and password, returns JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @PostMapping(value = "/sign-in", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<JwtResponse>> authenticateUser(@Valid @RequestBody final LoginDto loginDto) {
        log.info("Authenticating user with username/email: {}", loginDto.getUsernameOrEmail());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getEmail(), roles);
        return ResponseEntity.ok(GenericResponse.success("Authentication successful", jwtResponse));
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
