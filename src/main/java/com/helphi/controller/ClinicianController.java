package com.helphi.controller;


import com.helphi.api.Gp;
import com.helphi.api.user.Clinician;
import com.helphi.svc.ClinicianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ClinicianController {

    private final ClinicianService clinicianService;

    public ClinicianController(ClinicianService clinicialService){
        this.clinicianService = clinicialService;
    }



    @Operation(summary = "Get Clinician")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clinician.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content) })
    @GetMapping(value = "/clinician/{clinicianId}")
    public Clinician getClinician(@PathVariable String clinicianId) {
        return this.clinicianService.getClinician(UUID.fromString(clinicianId));
    }
    @Operation(summary = "Create a new Clinician")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clinical Created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clinician.class)) }),
            @ApiResponse(responseCode = "400", description = "Clinician could not be created",
                    content = @Content) })
    @PostMapping(value = "/clinician")
    public Clinician createClinician(@RequestBody Clinician clinician) {
        return this.clinicianService.createClinician(clinician);
    }

    @Operation(summary = "update new Clinician")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clinical updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clinician.class)) }),
            @ApiResponse(responseCode = "400", description = "Clinician could not be updated",
                    content = @Content) })
    @PutMapping(value = "/clinician")
    public Clinician updateClinician(@RequestBody Clinician clinician) {
        return this.clinicianService.updateClinician(clinician);
    }

    @Operation(summary = "delete Clinician")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clinical deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clinician.class)) }),
            @ApiResponse(responseCode = "400", description = "Clinician could not be updated",
                    content = @Content) })
    @DeleteMapping(value = "/clinician/{clinicianId}")
    public ResponseEntity<?> deleteClinician(@PathVariable String clinicianId) {
        this.clinicianService.deleteClinician(UUID.fromString(clinicianId));
        return ResponseEntity.ok().build();
    }
}
