package com.helphi.controller;

import com.helphi.api.HealthCondition;
import com.helphi.svc.HealthConditionService;
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
public class HealthConditionController {

    private final HealthConditionService conditionService;

    @Autowired
    public HealthConditionController(HealthConditionService healthConditionService) {
        this.conditionService = healthConditionService;
    }

    @Operation(summary = "Get a health condition by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Health Condition",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = HealthCondition.class)) }),
         @ApiResponse(responseCode = "404", description = "Health Condition not found",
            content = @Content) })
    @GetMapping(value = "/condition/{conditionId}")
    public ResponseEntity<HealthCondition> getCondition(@PathVariable(name = "conditionId") String conditionId) {
        Optional<HealthCondition> condition = this.conditionService.getCondition(UUID.fromString(conditionId));
        return condition.map(healthCondition -> ResponseEntity.ok().body(healthCondition)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/condition")
    public HealthCondition createCondition(@RequestBody HealthCondition condition) {
        return this.conditionService.createCondition(condition);
    }

    @PutMapping(value = "/condition")
    public HealthCondition updateCondition(@RequestBody HealthCondition condition) {
        return this.conditionService.updateCondition(condition);
    }

    @DeleteMapping(value = "/name/{conditionId}")
    public void deleteCondition(@PathVariable(name = "conditionId") String conditionId) {
        this.conditionService.deleteCondition(UUID.fromString(conditionId));
    }
}
