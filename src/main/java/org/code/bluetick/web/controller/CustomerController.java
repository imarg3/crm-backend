package org.code.bluetick.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.service.CustomerServiceImpl;
import org.code.bluetick.web.payload.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/customer")
@Slf4j
@AllArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers(Pageable pageable) {
        Page<Customer> page = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> createCustomer(@Valid Customer customer) {
        return ResponseEntity.status(201).body(GenericResponse.builder()
                .message("Customer is successfully created.")
                .status(HttpStatus.OK)
                .build());
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<GenericResponse> findCustomerByEmailId(@PathVariable String emailId) {

        Customer customer = customerService.findCustomerByEmail(emailId);
        return ResponseEntity.status(200).body(GenericResponse.builder()
                .message("Customer found: " + customer)
                .status(HttpStatus.OK)
                .build());
    }

    @PutMapping(value = "/update/{emailId}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@PathVariable String emailId, @Valid Customer customerUpdate, Principal principal){
        log.info(principal.getName());
        Customer customer  = customerService.findCustomerByEmail(emailId);
        Customer updatedCustomer = Customer.builder()
                .id(customer.getId())
                .name(customerUpdate.getName())
                .email(customerUpdate.getEmail())
                .mobile(customerUpdate.getMobile())
                .build();

        customerService.createNewCustomer(updatedCustomer);

        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
    }
}
