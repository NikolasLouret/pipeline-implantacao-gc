package com.nikolaslouret.apiclinicamedica.repositories;

import com.nikolaslouret.apiclinicamedica.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    /**
     * Finds patients by cpf.
     *
     * @param cpf The cpf of the patient.
     * @return A patient matching the criteria.
     */
    Optional<Patient> findByCpf(String cpf);
}
