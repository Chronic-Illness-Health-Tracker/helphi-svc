package com.helphi.controller;

import com.helphi.api.user.BaseUser;
import com.helphi.api.user.Patient;
import com.helphi.api.user.Registration;
import com.helphi.api.user.UserTypes;
import com.helphi.svc.RegistrationService;
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
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Operation(summary = "Link a user auth account to a user via an access code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Linked successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "500", description = "Could not link to user",
                    content = @Content) })
    @PutMapping(value = "/register/{registrationCode}")
    public ResponseEntity<?> registerUser(@PathVariable(name = "registrationCode" ) String registrationCode, @RequestBody String userId) {
        BaseUser user = this.registrationService.linkAuthToUser(userId, registrationCode);
        return user == null ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }

    @Operation(summary = "Link a user auth account to a user via an access code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Linked successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "500", description = "Could not link to user",
                    content = @Content) })
    @PutMapping(value = "/register/generate/{userId}")
    public ResponseEntity<Patient> createRegistrationCode(@PathVariable(name = "userId" ) String userId, @RequestParam UserTypes userType) {
        Registration registration = this.registrationService.createNewRegistration(UUID.fromString(userId), userType);
        return registration == null ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }
}
