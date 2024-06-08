package com.nikolaslouret.apiclinicamedica.steps;

import com.nikolaslouret.apiclinicamedica.utils.ReflectionUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import com.nikolaslouret.apiclinicamedica.models.Patient;
import com.nikolaslouret.apiclinicamedica.models.dtos.PatientDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PatientSteps {

    private final TestRestTemplate restTemplate;
    private Patient patient;
    private PatientDTO newPatient;

    public PatientSteps(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private ResponseEntity<?> responseEntity;
    private final String baseUrl = "http://localhost:8081/api/v1/patients";

    @Given("a patient exists with CPF {string}")
    public void aPatientExistsWithCpf(String cpf) {
        newPatient = new PatientDTO("Nikolas", "Louret", "M", LocalDate.now().minusYears(21), (short) 170, 70.5, cpf);
        responseEntity = restTemplate.postForEntity(baseUrl, newPatient, Patient.class);
    }

    @Given("a patient exists with CPF {string} having height {short}, weight {double} and gender {string}")
    public void aPatientExistsWithCpfHavingHeightWeightAndGender(String cpf, Short height, Double weight, String gender) {
        newPatient = new PatientDTO("Nikolas", "Louret", gender, LocalDate.now().minusYears(21), height, weight, cpf);
        responseEntity = restTemplate.postForEntity(baseUrl, newPatient, Patient.class);
    }


    @When("I request all patients")
    public void iRequestAllPatients() {
        responseEntity = restTemplate.getForEntity(baseUrl, List.class);
    }

    @When("I request the patient with CPF {string}")
    public void iRequestThePatientWithCpf(String cpf) {
        responseEntity = restTemplate.getForEntity(baseUrl + "/cpf/{cpf}", Patient.class, cpf);
    }

    @When("I create a patient with name {string}, surname {string}, gender {string}, birthDate {string}, height {int}, weight {double}, and CPF {string}")
    public void iCreateAPatientWithNameSurnameGenderBirthDateHeightWeightAndCPF(String name, String surname, String gender, String birthDate, int height, double weight, String cpf) {
        newPatient = new PatientDTO(name, surname, gender, LocalDate.parse(birthDate), (short) height, weight, cpf);
        responseEntity = restTemplate.postForEntity(baseUrl, newPatient, Patient.class);
    }

    @When("I update the patient with CPF {string} to have name {string}, surname {string}, gender {string}, birthDate {string}, height {int} and weight {double}")
    public void iUpdateThePatientWithCpfToHaveNameSurnameGenderBirthDateHeightWeightAndCPF(String cpf, String name, String surname, String gender, String birthDate, int height, double weight) {
        newPatient = new PatientDTO(name, surname, gender, LocalDate.parse(birthDate), (short) height, weight, cpf);

        UUID id = getIdByCpf(cpf);

        restTemplate.put(baseUrl + "/{id}", newPatient, id);
        responseEntity = restTemplate.getForEntity(baseUrl + "/{id}", Patient.class, id);
    }

    @When("I delete the patient with CPF {string}")
    public void iDeleteThePatientWithCpf(String cpf) {
        restTemplate.delete(baseUrl + "/{id}", getIdByCpf(cpf));
        responseEntity = ResponseEntity.noContent().build();
    }


    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {
        assertEquals(statusCode, responseEntity.getStatusCode().value());
    }

    @And("the response should contain a list of patients")
    public void theResponseShouldContainAListOfPatients() {
        assertNotNull(responseEntity.getBody());
        assertInstanceOf(List.class, responseEntity.getBody());
    }

    @Then("the response should contain the patient details")
    public void theResponseShouldContainThePatientDetails() {
        patient = (Patient) responseEntity.getBody();
        assertNotNull(patient);
        assertInstanceOf(Patient.class, patient);

        ReflectionUtils.assertFieldsEqual(newPatient, patient);
    }

    @And("the response should contain the IMC {double}")
    public void theResponseShouldContainTheImc(Double imc) {
        patient = (Patient) responseEntity.getBody();
        assertNotNull(patient);

        assertEquals(imc, patient.getImc());
    }

    @And("the response should contain the IMC classification {string}")
    public void theResponseShouldContainTheIMCClassification(String imcStatus) {
        patient = (Patient) responseEntity.getBody();
        assertNotNull(patient);

        assertEquals(imcStatus, patient.getIMCStatus());
    }


    private UUID getIdByCpf(String cpf) {
        responseEntity = restTemplate.getForEntity(baseUrl + "/cpf/{cpf}", Patient.class, cpf);

        patient = (Patient) responseEntity.getBody();
        assert patient != null;
        return patient.getId();
    }
}