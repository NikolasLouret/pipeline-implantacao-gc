package com.nikolaslouret.apiclinicamedica.services;

import com.nikolaslouret.apiclinicamedica.models.Patient;
import com.nikolaslouret.apiclinicamedica.models.dtos.PatientDTO;
import com.nikolaslouret.apiclinicamedica.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final Validator validator;

    public PatientService(PatientRepository patientRepository, Validator validator) {
        this.patientRepository = patientRepository;
        this.validator = validator;
    }

    public List<Patient> getAll() {
        return this.patientRepository.findAll();
    }

    public Patient getById(UUID id) {
        return this.patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    public Patient create(PatientDTO patient) {
        Patient newPatient = new Patient(patient);
        this.validatePatient(newPatient);

        return this.patientRepository.save(newPatient);
    }

    @Transactional
    public Patient update(UUID id, PatientDTO newPatient) {
        Patient patient = this.getById(id);
        this.validatePatient(patient);

        patient.setHeight(newPatient.height());
        patient.setName(newPatient.name());
        patient.setWeight(newPatient.weight());
        patient.setGender(newPatient.gender());
        patient.setSurname(newPatient.surname());
        patient.setBirthDate(newPatient.birthDate());

        return patientRepository.save(patient);
    }

    private void validatePatient(Patient patient) {
        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }
    }

    public void delete(UUID id) {
        this.patientRepository.deleteById(id);
    }
}
