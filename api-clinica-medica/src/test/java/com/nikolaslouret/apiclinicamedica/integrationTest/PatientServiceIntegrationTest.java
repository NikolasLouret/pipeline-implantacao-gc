package com.nikolaslouret.apiclinicamedica.integrationTest;

import com.nikolaslouret.apiclinicamedica.models.Patient;
import com.nikolaslouret.apiclinicamedica.models.dtos.PatientDTO;
import com.nikolaslouret.apiclinicamedica.repositories.PatientRepository;
import com.nikolaslouret.apiclinicamedica.services.PatientService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
public class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    private Patient testPatient;

    @BeforeEach
    public void setUp() {
        PatientDTO testPatientDTO = new PatientDTO("José", "Sobrenome", "M", LocalDate.of(1990, 5, 24), (short) 170, 70.5, "123.456.789-00");
        testPatient = patientService.create(testPatientDTO);
    }

    @Test
    public void testCreatePatient() {

        assertNotNull(testPatient.getId());
        assertEquals("José", testPatient.getName());

        Optional<Patient> savedPatient = patientRepository.findById(testPatient.getId());
        assertTrue(savedPatient.isPresent());
        assertEquals("José", savedPatient.get().getName());
    }

    @Test
    public void testUpdatePatient() {

        PatientDTO updatedPatientDTO = new PatientDTO("Novo Nome", "Sobrenome", "M", LocalDate.of(1990, 5, 24), (short) 170, 70.5, "123.456.789-00");
        Patient updatedPatient = patientService.update(testPatient.getId(), updatedPatientDTO);

        assertNotNull(updatedPatient);
        assertEquals("Novo Nome", updatedPatient.getName());

        Optional<Patient> updatedPatientInDb = patientRepository.findById(testPatient.getId());
        assertTrue(updatedPatientInDb.isPresent());
        assertEquals("Novo Nome", updatedPatientInDb.get().getName());
    }

    @Test
    public void testGetPatientById() {
        Optional<Patient> foundPatient = Optional.ofNullable(patientService.getById(testPatient.getId()));
        assertTrue(foundPatient.isPresent());

        assertEquals(testPatient.getId(), foundPatient.get().getId());
        assertEquals("José", foundPatient.get().getName());
    }

    @Test
    public void testValidationOnCreate() {
        PatientDTO invalidPatientDTO = new PatientDTO(null, "Sobrenome", "M", LocalDate.now(), (short) 1.70, 70.5, "123.456.789-00");

        assertThrows(ValidationException.class, () -> patientService.create(invalidPatientDTO));
    }

    @Test
    public void testValidationOnUpdate() {
        PatientDTO updatedPatientDTO = new PatientDTO(null, "Sobrenome", "M", LocalDate.now(), (short) 170, 70.5, "123.456.789-00");

        assertThrows(ValidationException.class, () -> patientService.update(testPatient.getId(), updatedPatientDTO));
    }

    @Test
    public void testDeletePatient() {
        patientService.delete(testPatient.getId());

        Optional<Patient> deletedPatient = patientRepository.findById(testPatient.getId());
        assertFalse(deletedPatient.isPresent());
    }
}

