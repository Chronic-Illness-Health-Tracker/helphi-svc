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
                            schema = @Schema(implementation = BaseUser.class)) }),
            @ApiResponse(responseCode = "500", description = "Could not link to user",
                    content = @Content) })
    @PutMapping(value = "/register")
    public BaseUser registerUser(@RequestParam String registrationCode, @RequestParam String authId) {
        return this.registrationService.linkAuthToUser(authId, registrationCode);
    }

    @Operation(summary = "Check if a code is valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code valid",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)) })
    })
    @PutMapping(value = "/register/{registrationCode}/valid")
    public ResponseEntity<Boolean> codeValid(@PathVariable(name = "registrationCode" ) String registrationCode) {
        boolean valid = this.registrationService.checkCodeValid(registrationCode);
        return ResponseEntity.ok().body(valid);
    }

    @Operation(summary = "Create a new registration code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Registration.class)) }),
            @ApiResponse(responseCode = "500", description = "Could not create code",
                    content = @Content) })
    @PostMapping(value = "/register/generate/{userId}")
    public Registration createRegistrationCode(@PathVariable(name = "userId" ) String userId, @RequestParam UserTypes userType) {
        return this.registrationService.createNewRegistration(UUID.fromString(userId), userType);
    }
}
