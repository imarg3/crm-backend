package org.code.bluetick.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.service.CustomerServiceImpl;
import org.code.bluetick.web.mapstruct.dto.CustomerDto;
import org.code.bluetick.web.mapstruct.dto.CustomerResponseDto;
import org.code.bluetick.web.payload.GenericResponse;
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
@RequestMapping("/api/v1/customers")
@Slf4j
@AllArgsConstructor
@Tag(name = "Customers", description = "Customer management operations")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {
    private final CustomerServiceImpl customerService;

    @GetMapping
    public ResponseEntity<GenericResponse<List<CustomerResponseDto>>> getAllCustomers(Pageable pageable) {
        List<CustomerResponseDto> customers = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(GenericResponse.success("Customers retrieved successfully", customers));
    }

    @Operation(summary = "Create a new customer", 
               description = "Creates a new customer with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Customer created successfully",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class))),
        @ApiResponse(responseCode = "409", description = "Customer already exists",
                     content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<CustomerResponseDto>> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerResponseDto savedCustomer = customerService.createNewCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse
                .success("Customer created successfully", savedCustomer));
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<GenericResponse<CustomerResponseDto>> findCustomerByEmailId(@PathVariable String emailId) {
        CustomerResponseDto customer = customerService.findCustomerByEmail(emailId);
        return ResponseEntity.ok(GenericResponse.success("Customer found", customer));
    }

    @PutMapping(value = "/{emailId}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<CustomerResponseDto>> updateCustomer(@PathVariable String emailId, 
                                                                               @Valid @RequestBody CustomerDto customerUpdate, 
                                                                               Principal principal) {
        log.info("Updating customer with email: {} by user: {}", emailId, principal.getName());
        CustomerResponseDto updatedCustomer = customerService.updateCustomer(emailId, customerUpdate);
        return ResponseEntity.ok(GenericResponse.success("Customer updated successfully", updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok(GenericResponse.success("Customer deleted successfully", null));
    }
}
