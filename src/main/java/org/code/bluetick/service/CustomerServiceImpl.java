package org.code.bluetick.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.persistence.repository.CustomerRepository;
import org.code.bluetick.web.exception.CustomerNotFoundException;
import org.code.bluetick.web.mapstruct.dto.CustomerDto;
import org.code.bluetick.web.mapstruct.dto.CustomerResponseDto;
import org.code.bluetick.web.mapstruct.mapper.MapStructMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    public CustomerResponseDto createNewCustomer(CustomerDto customerDto) {
        log.info("Creating new customer with email: {}", customerDto.getEmail());
        Customer customer = mapStructMapper.customerDtoToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return mapStructMapper.customerToCustomerResponseDto(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto findCustomerByEmail(final String email) {
        log.debug("Finding customer by email: {}", email);
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        Customer customer = optionalCustomer.orElseThrow(() -> 
            new CustomerNotFoundException("No customer found with email: " + email));
        return mapStructMapper.customerToCustomerResponseDto(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto findCustomerByMobile(String mobile) {
        log.debug("Finding customer by mobile: {}", mobile);
        Optional<Customer> optionalCustomer = customerRepository.findByMobile(mobile);
        Customer customer = optionalCustomer.orElseThrow(() -> 
            new CustomerNotFoundException("No customer found with mobile number: " + mobile));
        return mapStructMapper.customerToCustomerResponseDto(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getAllCustomers(Pageable pageable) {
        log.debug("Retrieving all customers with pagination");
        Page<Customer> customerPage = customerRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                ));
        return mapStructMapper.customerListToCustomerResponseDtoList(customerPage.getContent());
    }

    @Override
    public CustomerResponseDto updateCustomer(String email, CustomerDto customerDto) {
        log.info("Updating customer with email: {}", email);
        Customer existingCustomer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with email: " + email));
        
        // Update fields
        existingCustomer.setName(customerDto.getName());
        existingCustomer.setMobile(customerDto.getMobile());
        existingCustomer.setBirthDate(customerDto.getBirthDate());
        
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return mapStructMapper.customerToCustomerResponseDto(updatedCustomer);
    }

    @Override
    public void deleteCustomerById(Long id) {
        log.info("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("No customer found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
