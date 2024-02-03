package com.helphi.controller;

import com.helphi.api.HealthCondition;
import com.helphi.api.organisation.Organisation;
import com.helphi.svc.OrganisationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class OrganisationController {

    private final OrganisationService organisationService;

    @Autowired
    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @Operation(summary = "Get a Organisation by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Organisation",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Organisation.class)) }),
        @ApiResponse(responseCode = "404", description = "Organisation not found",
            content = @Content) })
    @GetMapping(value = "/organisation/{organisationId}")
    public ResponseEntity<Organisation> getOrganisation(@PathVariable(name = "organisationId") String organisationId) {
        Optional<Organisation> organisation = this.organisationService.getOrganisation(UUID.fromString(organisationId));
        return organisation.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "create a Organisation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "created the Organisation",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Organisation.class)) }),
        @ApiResponse(responseCode = "400", description = "Organisation cannot be created",
            content = @Content) })
    @PostMapping(value = "/organisation")
    public Organisation createOrganisation(@RequestBody Organisation organisation) {
        return this.organisationService.createOrganisation(organisation);
    }

    @Operation(summary = "update a Organisation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "updated the Organisation",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Organisation.class)) }),
        @ApiResponse(responseCode = "404", description = "Organisation cannot be found",
            content = @Content) })
    @PutMapping(value = "/organisation")
    public Organisation updateOrganisation(@RequestBody Organisation organisation) {
        return this.organisationService.updateOrganisation(organisation);
    }

    @Operation(summary = "delete a Organisation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "deleted the Organisation"),
        @ApiResponse(responseCode = "404", description = "Organisation cannot be found",
            content = @Content) })
    @DeleteMapping(value = "/organisation/{organisationId}")
    public void deleteOrganisation(@PathVariable(name = "organisationId") String organisationId) {
        this.organisationService.deleteOrganisation(UUID.fromString(organisationId));
    }


}
