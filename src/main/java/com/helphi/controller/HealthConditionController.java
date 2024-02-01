package com.helphi.controller;

import com.helphi.api.HealthCondition;
import com.helphi.svc.HealthConditionService;
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

    @GetMapping(name = "/condition/{conditionId}")
    public ResponseEntity<HealthCondition> getCondition(@RequestParam(name = "conditionId") String conditionId) {
        Optional<HealthCondition> condition = this.conditionService.getCondition(UUID.fromString(conditionId));
        if(condition.isPresent()) {
            return ResponseEntity.ok().body(condition.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(name = "/condition")
    public HealthCondition createCondition(@RequestBody HealthCondition condition) {
        return this.conditionService.createCondition(condition);
    }

    @PutMapping(name = "/condition")
    public HealthCondition updateCondition(@RequestBody HealthCondition condition) {
        return this.conditionService.updateCondition(condition);
    }

    @DeleteMapping(name = "/name/{conditionId}")
    public void deleteCondition(@RequestParam(name = "conditionId") String conditionId) {
        this.conditionService.deleteCondition(UUID.fromString(conditionId));
    }
}
