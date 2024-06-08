package com.nikolaslouret.apiclinicamedica.configs;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import com.nikolaslouret.apiclinicamedica.ApiClinicaMedicaApplication;

@CucumberContextConfiguration
@ContextConfiguration(classes = ApiClinicaMedicaApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {

    @Bean
    public TestRestTemplate testRestTemplate() {
        return new TestRestTemplate();
    }
}