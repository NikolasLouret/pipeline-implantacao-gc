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

        return this.patientRepository.save(newPatient);
    }

    @Transactional
    public Patient update(UUID id, PatientDTO newPatient) {
        Patient patient = this.getById(id);

        if (isPatientValid(newPatient)) {
            patient.setHeight(newPatient.height());
            patient.setName(newPatient.name());
            patient.setWeight(newPatient.weight());
            patient.setGender(newPatient.gender());
            patient.setSurname(newPatient.surname());
            patient.setBirthDate(newPatient.birthDate());

            return patientRepository.save(patient);
        }

        throw new ValidationException("Dados do paciente inválidos");
    }

    private boolean isPatientValid(PatientDTO patientDTO) {
        Set<ConstraintViolation<PatientDTO>> violations = validator.validate(patientDTO);

        return violations.isEmpty();
    }

    public void delete(UUID id) {
        this.patientRepository.deleteById(id);
    }
}
