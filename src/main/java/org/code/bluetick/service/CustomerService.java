package org.code.bluetick.service;

import org.code.bluetick.web.mapstruct.dto.CustomerDto;
import org.code.bluetick.web.mapstruct.dto.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createNewCustomer(CustomerDto customerDto);

    CustomerResponseDto findCustomerByEmail(String email);

    CustomerResponseDto findCustomerByMobile(String mobile);

    List<CustomerResponseDto> getAllCustomers(Pageable pageable);

    CustomerResponseDto updateCustomer(String email, CustomerDto customerDto);

    void deleteCustomerById(Long id);
}
