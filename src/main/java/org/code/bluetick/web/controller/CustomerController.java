package org.code.bluetick.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/v1/api/customer")
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers(Pageable pageable) {
        Page<Customer> page = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@Valid Customer customer) {
        return ResponseEntity.status(201).body(customerService.createCustomer(customer));
    }

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
}
