package com.nikolaslouret.apiclinicamedica.controllers;

import com.nikolaslouret.apiclinicamedica.models.Patient;
import com.nikolaslouret.apiclinicamedica.models.dtos.PatientDTO;
import com.nikolaslouret.apiclinicamedica.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientService patientService;

    @Tag(name = "GET", description = "GET methods of Patient APIs")
    @Operation(summary = "Get all patients", description = "Returns a list of all registered patients")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Patient not found",
                    content = @Content) })
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            return ResponseEntity.ok().body(this.patientService.getAll());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Tag(name = "GET", description = "GET methods of Patient APIs")
    @Operation(summary = "Get patient by ID", description = "Returns a patient by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Patient not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@Parameter(
            description = "ID of patient to be retrieved",
            required = true) @PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(this.patientService.getById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Tag(name = "GET", description = "GET methods of Patient APIs")
    @Operation(summary = "Get patient by CPF", description = "Returns a patient by their CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Patient not found",
                    content = @Content) })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Patient> getPatientByCpf(@Parameter(
            description = "CPF of patient to be retrieved",
            required = true) @PathVariable String cpf) {
        try {
            return ResponseEntity.ok().body(this.patientService.getByCpf(cpf));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Tag(name = "POST", description = "POST methods of Patient APIs")
    @Operation(summary = "Create a new patient", description = "Creates a new patient and returns their data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Patient not found",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Patient> createPatient(@Parameter(
            description = "The patient data to be created",
            required = true) @Valid @RequestBody PatientDTO patient) {
        try {
            Patient test = this.patientService.create(patient);
            return ResponseEntity.ok().body(test);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Tag(name = "PUT", description = "PUT methods of Patient APIs")
    @Operation(summary = "Update a patient", description = "Update the data of an existing patient by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Patient not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@Parameter(
            description = "ID of patient to be updated",
            required = true) @PathVariable UUID id, @Parameter(
            description = "The patient data to be updated",
            required = true) @Valid @RequestBody PatientDTO patient) {
        try {
            Patient updatedPatient = this.patientService.update(id, patient);
            return ResponseEntity.ok().body(updatedPatient);
        } catch (EntityNotFoundException | ValidationException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Tag(name = "DELETE", description = "DELETE methods of Patient APIs")
    @Operation(summary = "Delete a patient", description = "Deletes the data of an existing patient by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "400", description = "Patient not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@Parameter(
            description = "ID of patient to be deleted",
            required = true) @PathVariable UUID id) {
        try {
            this.patientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
