package org.code.bluetick.web.controller;

import org.code.bluetick.DatabaseTest;
import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.web.mapstruct.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class UserRegistrationControllerTest extends DatabaseTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("register a new user")
    void shouldCreateANewUser() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("fullName","New user 1");
        map.add("businessName","Company-1");
        map.add("email","user1@gmail.com");
        map.add("mobile", "8899889989");
        map.add("password", "Arpit!3988");
        map.add("matchingPassword", "Arpit!3988");
        map.add("country","India");
        map.add("state","Maharashtra");
        map.add("city", "Pune");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        System.out.println("Headers are " + entity.getHeaders());
        ResponseEntity<UserDto> createResponse = restTemplate.withBasicAuth("arpit", "arpit123").postForEntity("/v1/api/user/registration", entity, UserDto.class);
        //assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        System.out.println(createResponse);
    }
}