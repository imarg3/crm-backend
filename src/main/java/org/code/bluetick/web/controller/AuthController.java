package org.code.bluetick.web.controller;

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
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final MapStructMapper mapStructMapper;

    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    private final JwtUtils jwtUtils;

    @PostMapping(value = "/sign-up", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> registerUserAccount(@Valid final UserDto userDto, final HttpServletRequest request) {
        log.info("Registering user account with information: {}",  userDto);

        final User registered = userService.registerNewUserAccount(mapStructMapper.userDtoToUser(userDto));
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));

        return ResponseEntity.status(201).body(GenericResponse.builder()
                .message("User is successfully registered.")
                .status(HttpStatus.OK)
                .build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid final LoginDto loginDto) {
        log.info("Authenticating user account with information: {}",  loginDto);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getEmail(),
                roles));
    }
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
