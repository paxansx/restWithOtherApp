package com.example.rest.controller.rest;


import com.example.rest.modal.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Rest {
    private static final String allUsersApi = "http://91.241.64.178:7081/api/users";
    private static final String addUserApi = "http://91.241.64.178:7081/api/users";
    private static final String updateUserApi = "http://91.241.64.178:7081/api/users";
    private static final String deleteUserApi = "http://91.241.64.178:7081/api/users/";
    final RestTemplate restTemplate;

    public Rest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @GetMapping("/users")
    public void getCode() {

        ResponseEntity<String> entity = restTemplate.getForEntity(allUsersApi, String.class);
        String cookie = entity.getHeaders().get("Set-Cookie").get(0);
        User user = new User(3, "James", "Brown", (byte) 32);
        User user1 = new User(3, "Thomas", "Shelby", (byte) 32);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);


        HttpEntity<?> requestBody = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(addUserApi, HttpMethod.POST, requestBody, String.class);
        System.out.println(response);



        HttpEntity<?> requestBody2 = new HttpEntity<>(user1, headers);
        System.out.println(restTemplate.exchange(updateUserApi, HttpMethod.PUT, requestBody2, String.class).getBody());
        HttpEntity<?> requestBody3 = new HttpEntity<>(null, headers);

        System.out.println(restTemplate.exchange(deleteUserApi + user.getId(), HttpMethod.DELETE, requestBody3, String.class).getBody());
    }

}
