package com.nikolaslouret.apiclinicamedica.models.dtos;

import java.time.LocalDate;

public record PatientDTO(
        String name,
        String surname,
        char gender,
        LocalDate birthDate,
        Short height,
        Double weight,
        String cpf
) { }
