package com.nikolaslouret.apiclinicamedica.models.dtos;

import java.time.LocalDate;

public record PatientDTO(String name, String surname, char gender, LocalDate birthDate, short height, double weight, String cpf) {
}
