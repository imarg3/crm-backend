package org.code.bluetick.service;

import org.code.bluetick.persistence.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Customer createNewCustomer(Customer user);

    Customer findCustomerByEmail(String email);

    Customer findCustomerByMobile(String mobile);

    Page<Customer> getAllCustomers(Pageable pageable);

    Customer updateCustomer(Customer user);
}
