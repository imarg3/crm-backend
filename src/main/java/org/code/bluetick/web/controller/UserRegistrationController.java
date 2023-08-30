package org.code.bluetick.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.persistence.model.User;
import org.code.bluetick.registration.OnRegistrationCompleteEvent;
import org.code.bluetick.service.IUserService;
import org.code.bluetick.service.UserService;
import org.code.bluetick.web.mapstruct.dto.UserDto;
import org.code.bluetick.web.mapstruct.mapper.MapStructMapper;
import org.code.bluetick.web.util.GenericResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/v1/api/user")
@Slf4j
public class UserRegistrationController {
    private final MapStructMapper mapStructMapper;

    private final IUserService userService;

    private final ApplicationEventPublisher eventPublisher;

    public UserRegistrationController(MapStructMapper mapStructMapper, UserService userService, ApplicationEventPublisher eventPublisher) {
        this.mapStructMapper = mapStructMapper;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping(value = "/registration", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse registerUserAccount(@Valid final UserDto userDto, final HttpServletRequest request) {
        log.info("Registering user account with information: {}",  userDto);

        final User registered = userService.registerNewUserAccount(mapStructMapper.userDtoToUser(userDto));
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));

        // return ResponseEntity.status(201).body(customerService.createCustomer(customer));
        return new GenericResponse("success");
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(Pageable pageable) {
        Page<User> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    /*
    @GetMapping("/{emailId}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable String emailId) {
        Optional<Customer> customerOptional = customerService.findCustomerByEmailId(emailId);
        return customerOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/update/{emailId}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@PathVariable String emailId, @Valid Customer customerUpdate, Principal principal){
        log.info(principal.getName());
        Optional<Customer> customerOptional = customerService.findCustomerByEmailId(emailId);
        if(customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            Customer updatedCustomer = Customer.builder()
                    .id(customer.getId())
                    .name(customerUpdate.getName())
                    .email(customerUpdate.getEmail())
                    .mobile(customerUpdate.getMobile())
                    .build();

            customerService.createCustomer(updatedCustomer);

            return ResponseEntity.ok(updatedCustomer);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
    }
    */

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
