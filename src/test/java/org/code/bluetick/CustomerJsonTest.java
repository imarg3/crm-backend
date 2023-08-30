package org.code.bluetick;

import org.assertj.core.util.Arrays;
import org.code.bluetick.persistence.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
class CustomerJsonTest {
    @Autowired
    private JacksonTester<Customer> json;

    @Autowired
    private JacksonTester<Customer[]> jsonList;

    private Customer[] customers;

    @BeforeEach
    void setUp() {
        Customer customer1 = Customer.builder().id(1L).name("Arpit Gupta").email("arpit@gmail.com").mobile("8989898989").build();
        Customer customer2 = Customer.builder().id(2L).name("Ram Gupta").email("ram@gmail.com").mobile("9876543210").build();
        Customer customer3 = Customer.builder().id(3L).name("Tony Stark").email("tonys@gmail.com").mobile("9876543210").build();

        customers = Arrays.array(
               customer1, customer2, customer3
        );
    }

    @Test
    @DisplayName("Customer object serialization test")
    void customerSerializationTest() throws IOException {
        Customer customer = customers[0];
        assertThat(json.write(customer)).isStrictlyEqualToJson("single_customer.json");
        assertThat(json.write(customer)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(customer)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
        assertThat(json.write(customer)).hasJsonPathStringValue("@.name");
        assertThat(json.write(customer)).extractingJsonPathStringValue("@.name").isEqualTo("Arpit Gupta");
    }

    @Test
    @DisplayName("Customer object deserialization test")
    void customerDeserializationTest() throws IOException {
        String expected = """
           {
                "id": 2,
                "name": "Ram Gupta",
                "email": "ram@gmail.com",
                "mobile": "9876543210"
          }
          """;
        //assertThat(json.parse(expected)).isEqualTo(new Customer(2L, "Ram Gupta", "ram@gmail.com", "9876543210"));
        assertThat(json.parseObject(expected).getId()).isEqualTo(2);
        assertThat(json.parseObject(expected).getName()).isEqualTo("Ram Gupta");
    }

    @Test
    @DisplayName("Customers list serialization test")
    void customerListSerializationTest() throws IOException {
        assertThat(jsonList.write(customers)).isStrictlyEqualToJson("customers-list.json");
    }

    @Test
    @DisplayName("Customers list deserialization test")
    void customerListDeserializationTest() throws IOException {
        String expected = """
                [{
                  "id": 1,
                  "name": "Arpit Gupta",
                  "email": "arpit@gmail.com",
                  "mobile": "8989898989"
                },
                {
                "id": 2,
                "name": "Ram Gupta",
                "email": "ram@gmail.com",
                "mobile": "9876543210"
                },
                {
                "id": 3,
                "name": "Tony Stark",
                "email": "tonys@gmail.com",
                "mobile": "9876543210"
                }]
                """;

        assertThat(jsonList.parseObject(expected)).hasSize(3);
    }
}
