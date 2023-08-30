package org.code.bluetick.service;

import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.persistence.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
        ));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> findCustomerByEmailId(String emailId) {
        return Optional.ofNullable(customerRepository.findByEmail(emailId));
    }

    public Customer updateCustomer(Customer user) {
        return customerRepository.save(user);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
