package com.helphi.controller;

import com.helphi.api.user.Patient;
import com.helphi.svc.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping(value = "/patient/{patientId}")
    public ResponseEntity<Patient> getPatient(@PathVariable(name = "patientId" ) String patientId) {
        Optional<Patient> patient =  this.patientService.getPatient(UUID.fromString(patientId));
        return patient.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/patient")
    public Patient createPatient(@RequestBody Patient patient) {
        return this.patientService.createPatient(patient);
    }

    @PutMapping(value = "/patient")
    public Patient updatePatient(@RequestBody Patient patient) {
        return this.patientService.updatePatient(patient);
    }

    @DeleteMapping(value = "/patient/{patientId}")
    public void deletePatient(@PathVariable(name = "patientId") String patientId) {
        this.patientService.deletePatient(UUID.fromString(patientId));
    }
}
