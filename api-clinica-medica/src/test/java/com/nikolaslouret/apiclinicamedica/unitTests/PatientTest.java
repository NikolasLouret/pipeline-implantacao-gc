package com.nikolaslouret.apiclinicamedica.unitTests;

import com.nikolaslouret.apiclinicamedica.models.Patient;
import com.nikolaslouret.apiclinicamedica.models.dtos.PatientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    private Patient patient;

    @BeforeEach
    public void setUp() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        PatientDTO dto = new PatientDTO("Name", "Surname", "M", birthDate, (short) 180, 70.0,"123.456.789-00");
        patient = new Patient(dto);
    }

    @Test
    public void testGetIdealWeight() {
        double expectedIdealWeight = Math.round(((72.7 * 1.80) - 58) * 100.0) / 100.0;
        assertEquals(expectedIdealWeight, patient.getIdealWeight());
    }

    @Test
    public void testCalculateIMC() {
        double expectedIMC = Math.round(70 / Math.pow(1.80, 2) * 100.0) / 100.0;
        assertEquals(expectedIMC, patient.getImc());
    }

    @Test
    public void testCalculateAge() {
        byte expectedAge = (byte) (LocalDate.now().getYear() - 1990);
        assertEquals(expectedAge, patient.getAge());
    }

    @Test
    public void testGetIMCStatus() {
        assertEquals("Peso normal", patient.getIMCStatus());

        patient.setWeight(50.0);
        assertEquals("Muito abaixo do peso", patient.getIMCStatus());

        patient.setWeight(90.0);
        assertEquals("Acima do peso", patient.getIMCStatus());

        patient.setWeight(100.0);
        assertEquals("Obesidade I", patient.getIMCStatus());

        patient.setWeight(120.0);
        assertEquals("Obesidade II (severa)", patient.getIMCStatus());

        patient.setWeight(150.0);
        assertEquals("Obesidade III (mórbida)", patient.getIMCStatus());
    }

    @Test
    public void testGetObfuscatedCPF() {
        String expectedObfuscatedCPF = "***.456.***-**";
        assertEquals(expectedObfuscatedCPF, patient.getObfuscatedCPF());
    }

    @Test
    public void testValidateCPF() {
        assertTrue(patient.validateCPF("968.690.256-22"));
        assertFalse(patient.validateCPF("98765432101"));
        assertFalse(patient.validateCPF("123.456.789-39"));
        assertFalse(patient.validateCPF("12345678901"));
    }
}
