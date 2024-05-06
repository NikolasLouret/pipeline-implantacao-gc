package com.nikolaslouret.apiclinicamedica.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikolaslouret.apiclinicamedica.models.Patient;
import com.nikolaslouret.apiclinicamedica.models.dtos.PatientDTO;
import com.nikolaslouret.apiclinicamedica.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    private PatientDTO patientDTO;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        patientDTO = new PatientDTO("José", "Sobrenome", "M", LocalDate.now(), (short) 170, 70.5, "123.456.789-00");
        patient = new Patient(patientDTO);
        patient.setId(UUID.randomUUID());
    }

    @Test
    public void testCreatePatient() throws Exception {
        when(patientService.create(patientDTO)).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllPatients() throws Exception {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);

        when(patientService.getAll()).thenReturn(patientList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPatientById() throws Exception {
        when(patientService.getById(patient.getId())).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/{id}", patient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patient.getName()));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        when(patientService.update(patient.getId(), patientDTO)).thenReturn(patient);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patients/{id}", patient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(patient.getName()));
    }

    @Test
    public void testDeletePatient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patients/{id}", patient.getId()))
                .andExpect(status().isNoContent());
    }
}
