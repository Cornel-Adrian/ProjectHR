package com.companyhr;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RequestTest {

    @LocalServerPort
    private int port=8089;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void controllerShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Welcome to our app");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/"+"login",
                String.class)).contains("Welcome!");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/"+"registration",
                String.class)).contains("Please fill in your registration details");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/"+"viewpersonaldetails",
                String.class)).contains("Welcome to your personal details page");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/"+"viewallvacancies",
                String.class)).contains("List of Vacancies");
    }
}