package org.code.bluetick;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.code.bluetick.persistence.model.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BluetickApplicationTests extends DatabaseTest{

    @Autowired
	TestRestTemplate restTemplate;

    /*
	@Test
	@Disabled
	void shouldNotReturnACustomerWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/svcrest/customer/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}


*/
    @Test
    @DisplayName("create a new customer")
    @Order(1)
    void shouldCreateANewCustomer() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //var customer1 = Customer.builder().id(1L).name("Arpit Gupta").email("arpit@gmail.com").mobile("8989898989").build();
        // build the request
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name","feature");
        map.add("email","feature@gmail.com");
        map.add("mobile", "8899889989");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        System.out.println("Headers are " + entity.getHeaders());
        ResponseEntity<Customer> createResponse = restTemplate.withBasicAuth("arpit", "arpit123").postForEntity("/v1/api/customer/create", entity, Customer.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        map.clear();
        map.add("name","tester");
        map.add("email","tester@gmail.com");
        map.add("mobile", "9898989898");
        entity = new HttpEntity<>(map, headers);
        System.out.println("Headers are " + entity.getHeaders());
        createResponse = restTemplate.withBasicAuth("arpit", "arpit123").postForEntity("/v1/api/customer/create", entity, Customer.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("should return all customers when list is requested")
    @Order(2)
    void shouldReturnAllCustomersWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("arpit", "arpit123").getForEntity("/v1/api/customer/all", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int customerCount = documentContext.read("$.length()");
        assertThat(customerCount).isEqualTo(2);
        System.out.println(response.getBody());

        JSONArray names = documentContext.read("$..name");
        assertThat(names).containsExactlyInAnyOrder("tester", "feature");
    }

    @Test
    @DisplayName("should return page of customers")
    @Order(2)
    void shouldReturnPageOfCustomers() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("arpit", "arpit123").getForEntity("/v1/api/customer/all?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page).hasSize(1);
    }

    @Test
    @DisplayName("should return sorted page of customers")
    @Order(3)
    void shouldReturnSortedPageOfCustomers() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("arpit", "arpit123").getForEntity("/v1/api/customer/all?page=0&size=1&sort=id,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page).hasSize(1);

        String name = documentContext.read("$[0].name");
        assertThat(name).isEqualTo("tester");
    }

    @Test
    @DisplayName("should return a customer when data is saved")
    @Order(4)
    void shouldReturnACustomerWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("arpit", "arpit123").getForEntity(("/v1/api/customer/tester@gmail.com"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse((response.getBody()));
        Number id = documentContext.read("$.id");
        assertThat(id).isNotNull();
    }

    @Test
    @Order(5)
    void shouldUpdateAnExistingCustomer() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //var customer1 = Customer.builder().id(1L).name("Arpit Gupta").email("arpit@gmail.com").mobile("8989898989").build();
        // build the request
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name","tester1");
        map.add("email","tester1@gmail.com");
        map.add("mobile", "8798798794");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<Customer> response = restTemplate.withBasicAuth("arpit", "arpit123").exchange("/v1/api/customer/update/tester@gmail.com", HttpMethod.PUT, request, Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate.withBasicAuth("arpit", "arpit123")
                .getForEntity("/v1/api/customer/tester1@gmail.com", String.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        String email = documentContext.read("$.email");
        assertThat(email).isEqualTo("tester1@gmail.com");
    }
}
