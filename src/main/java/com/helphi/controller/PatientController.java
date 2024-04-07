package com.helphi.controller;

import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.helphi.api.GpSurgery;
import com.helphi.api.HealthCondition;
import com.helphi.api.user.Patient;
import com.helphi.exception.NotFoundException;
import com.helphi.question.api.PatientStatus;
import com.helphi.question.api.UserResponse;
import com.helphi.svc.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @Operation(summary = "Get a Patient by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Patient",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Patient.class)) }),
        @ApiResponse(responseCode = "404", description = "Patient not found",
            content = @Content) })
    @GetMapping(value = "/patient/{patientId}")
    public ResponseEntity<Patient> getPatient(@PathVariable(name = "patientId" ) String patientId) {
        Optional<Patient> patient =  this.patientService.getPatient(UUID.fromString(patientId));
        return patient.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Patient created",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Patient.class)) }),
        @ApiResponse(responseCode = "400", description = "Could not create patient",
            content = @Content) })
    @PostMapping(value = "/patient")
    public Patient createPatient(@RequestBody Patient patient) {
        return this.patientService.createPatient(patient);
    }


    @Operation(summary = "Update an existing patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient updated",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Patient.class)) }),
        @ApiResponse(responseCode = "400", description = "Could not update patient",
            content = @Content) })
    @PutMapping(value = "/patient")
    public Patient updatePatient(@RequestBody Patient patient) {
        return this.patientService.updatePatient(patient);
    }


    @Operation(summary = "Update a patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Patient deleted",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Patient.class)) }),
        @ApiResponse(responseCode = "404", description = "Could not find patient",
            content = @Content) })
    @DeleteMapping(value = "/patient/{patientId}")
    public void deletePatient(@PathVariable(name = "patientId") String patientId) throws NotFoundException {
        this.patientService.deletePatient(UUID.fromString(patientId));
    }

    @Operation(summary = "Add a health condition to a patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Health condition added",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Patient.class)) }),
        @ApiResponse(responseCode = "404", description = "Could not find patient",
            content = @Content) })
    @PutMapping(value = "/patient/{patientId}/health-Condition")
    public Patient addHealthCondition(@PathVariable String patientId, @RequestBody HealthCondition healthCondition) throws NotFoundException {
        return this.patientService.addHealthCondition(UUID.fromString(patientId), healthCondition);
    }

    @Operation(summary = "Add a health condition to a patient by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Health condition added",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Patient.class)) }),
        @ApiResponse(responseCode = "404", description = "Could not find patient",
            content = @Content) })
    @PutMapping(value = "/patient/{patientId}/health-Condition/{conditionId}")
    public Patient addHealthConditionById(@PathVariable String patientId, @PathVariable String conditionId) throws NotFoundException {
        return this.patientService.addHealthConditionById(UUID.fromString(patientId), UUID.fromString(conditionId));
    }

    @Operation(summary = "Remove a health condition to a patient")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Health condition added",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Patient.class)) }),
        @ApiResponse(responseCode = "404", description = "Could not find entity",
            content = @Content) })
    @DeleteMapping(value = "/patient/{patientId}/health-Condition/{conditionId}")
    public Patient deleteHealthCondition(@PathVariable String patientId, @PathVariable String conditionId) throws NotFoundException {
        return this.patientService.deleteHealthConditionById(UUID.fromString(patientId), UUID.fromString(conditionId));
    }

    @Operation(summary = "Get all patients that have been linked to a condition")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of patients",
            content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Patient.class))) })
    })
    @GetMapping(value = "/patient/health-Condition/{conditionId}")
    public List<Patient> getPatientsInCondition(@PathVariable String conditionId){
        return this.patientService.getPatientsInCondition(UUID.fromString(conditionId));
    }

    @Operation(summary = "Get a list of patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of patients",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Patient.class))) })
    })
    @GetMapping(value = "/patient")
    public List<Patient> getPatients(@RequestParam Optional<String> value) {
        List<Patient> patients;
        if(value.isPresent()){
            patients = this.patientService.getPatients(value.get());
        } else {
            patients = this.patientService.getPatients();
        }

        if(patients.size()> 50) {
            return patients.subList(0, 49);
        } else {
            return  patients;
        }
    }

    @Operation(summary = "Provide and answer to a question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question submitted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Could not save response",
                    content = @Content) })
    @PostMapping(value = "/patient/answer")
    public UserResponse addAnswer(@RequestBody UserResponse response) throws NotFoundException {
        return this.patientService.addAnswerToQuestion(response);
    }

    @Operation(summary = "Get patients status over the last 30 days")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of patient statuses",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PatientStatus.class))) })
    })
    @GetMapping(value = "/patient/{patientId}/status/{conditionId}")
    public List<PatientStatus> getStatus(@PathVariable String patientId, @PathVariable String conditionId) throws NotFoundException {
        return this.patientService.getRecentPatientStatus(UUID.fromString(patientId), UUID.fromString(conditionId));
    }

    @Operation(summary = "Get patients most recent status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got status",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)) })
    })
    @GetMapping(value = "/patient/{patientId}/status/{conditionId}/recent")
    public PatientStatus getRecentStatus(@PathVariable String patientId, @PathVariable String conditionId) throws NotFoundException {
        return this.patientService.getMostRecentPatientStatus(UUID.fromString(patientId), UUID.fromString(conditionId));
    }
}
