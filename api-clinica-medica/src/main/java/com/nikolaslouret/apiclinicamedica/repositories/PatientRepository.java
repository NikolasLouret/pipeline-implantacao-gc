package com.nikolaslouret.apiclinicamedica.repositories;

import com.nikolaslouret.apiclinicamedica.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
}
