package org.code.bluetick.persistence.repository;

import org.code.bluetick.DatabaseTest;
import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JPA tests with datasource configured via standard properties files
 */
@DataJpaTest
class CustomerRepositoryTest extends DatabaseTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("find all the customers name")
    void testFindAllCustomersName() {
        var customer1 = Customer.builder()
                .id(1L)
                .name("Arpit Gupta")
                .email("arpit@gmail.com")
                .mobile("8989898989")
                .birthDate(LocalDate.of(1988, Month.SEPTEMBER, 3))
                .build();
        var customer2 = Customer.builder()
                .id(2L)
                .name("Ram Gupta")
                .email("ram@gmail.com")
                .mobile("9898989898")
                .birthDate(LocalDate.of(1987, Month.SEPTEMBER, 3))
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(2);
        assertThat(customers.get(0).getName()).isEqualTo("Arpit Gupta");
        assertThat(customers.get(1).getName()).isEqualTo("Ram Gupta");
    }
}