package com.helphi.controller;

import com.helphi.api.HealthCondition;
import com.helphi.api.organisation.Organisation;
import com.helphi.question.api.ConditionCheckIn;
import com.helphi.question.api.Question;
import com.helphi.svc.HealthConditionService;
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

    @Operation(summary = "List of Health conditions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Health Conditions",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HealthCondition.class))) })
    })
    @GetMapping(value = "/condition")
    public List<HealthCondition> listHealthConditions(@RequestParam(required = false) String healthConditionName) {
        return this.conditionService.listHealthConditionsByName(healthConditionName);
    }

    @Operation(summary = "Add a question to an existing health condition")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created question",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Question.class)) }),
            @ApiResponse(responseCode = "400", description = "Could not add question",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Health condition not found",
                    content = @Content)})
    @PostMapping(value = "/condition/{conditionId}/question")
    public ResponseEntity<Question> addQuestion(@PathVariable(name = "conditionId") String conditionId, @RequestBody Question question) {
        Question createdQuestion = this.conditionService.addQuestionToHealthCondition(UUID.fromString(conditionId), question);
        return ResponseEntity.status(202).body(createdQuestion);
    }

    @Operation(summary = "Update a check ins information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConditionCheckIn.class)) }),
            @ApiResponse(responseCode = "404", description = "Health condition not found",
                    content = @Content)})
    @PutMapping(value = "/condition/{conditionId}/checkin")
    public ResponseEntity<ConditionCheckIn> updateCheckIn(@PathVariable(name = "conditionId") String conditionId,
                                                         @RequestBody ConditionCheckIn checkIn) {
        this.conditionService.updateCheckIn(conditionId, checkIn);
        return ResponseEntity.ok(checkIn);
    }

    @Operation(summary = "get a check ins information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConditionCheckIn.class)) }),
            @ApiResponse(responseCode = "404", description = "Health condition not found",
                    content = @Content)})
    @GetMapping(value = "/condition/{conditionId}/checkin")
    public ResponseEntity<ConditionCheckIn> getCheckIn(@PathVariable(name = "conditionId") String conditionId) {
        ConditionCheckIn checkIn = this.conditionService.getCheckIn(UUID.fromString(conditionId));
        return ResponseEntity.ok(checkIn);
    }

    @Operation(summary = "List of Health condition questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Health Condition questions",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Question.class))) })
    })
    @GetMapping(value = "/condition/{conditionId}/questions")
    public List<Question> getHealthConditionQuestions(@PathVariable(name = "conditionId") String conditionId) {
        return this.conditionService.listConditionQuestions(UUID.fromString(conditionId));
    }

    @Operation(summary = "Update or add a  group of questions to an existing health condition")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questions added/updated",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Question.class))) })})
    @PutMapping(value = "/condition/{conditionId}/questions")
    public ResponseEntity<Question> updateQuestions(@PathVariable(name = "conditionId") String conditionId, @RequestBody List<Question> questions) {
        this.conditionService.addQuestionsToHealthCondition(UUID.fromString(conditionId), questions);
        return ResponseEntity.status(200).build();
    }
}
