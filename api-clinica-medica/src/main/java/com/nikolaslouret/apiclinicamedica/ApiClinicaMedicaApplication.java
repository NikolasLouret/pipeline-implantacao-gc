package com.nikolaslouret.apiclinicamedica;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Gerenciamento de Pacientes", version = "1.0", description = "API Java para gerenciar pacientes de uma clínica médica."))
public class ApiClinicaMedicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiClinicaMedicaApplication.class, args);
	}

}
