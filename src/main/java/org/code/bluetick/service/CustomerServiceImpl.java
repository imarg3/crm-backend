package org.code.bluetick.service;

import lombok.RequiredArgsConstructor;
import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.persistence.repository.CustomerRepository;
import org.code.bluetick.web.exception.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl  implements CustomerService{
    private final CustomerRepository customerRepository;

    @Override
    public Customer createNewCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerByEmail(final String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        Customer customer;
        if(optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        } else {
            throw new CustomerNotFoundException("No customer found with username: " + email);
        }

        return customer;
    }

    @Override
    public Customer findCustomerByMobile(String mobile) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobile(mobile);
        Customer customer;
        if(optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        } else {
            throw new UsernameNotFoundException("No customer found with mobile number: " + mobile);
        }

        return customer;
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
        ));
    }

    public Customer updateCustomer(Customer user) {
        return customerRepository.save(user);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
