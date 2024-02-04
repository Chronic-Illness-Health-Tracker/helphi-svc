package com.helphi.controller;

import com.helphi.api.Gp;
import com.helphi.api.GpSurgery;
import com.helphi.svc.GpService;
import com.helphi.svc.GpSurgeryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class GpSurgeryController {

    private final GpSurgeryService gpSurgeryService;

    public GpSurgeryController(GpSurgeryService gpSurgeryService) {
        this.gpSurgeryService = gpSurgeryService;
    }

    @Operation(summary = "Get a GP Surgery by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the GP Surgery",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GpSurgery.class)) }),
            @ApiResponse(responseCode = "404", description = "GP Surgery not found",
                    content = @Content) })
    @GetMapping(value = "/gp-surgery/{gpSurgeryId}")
    public ResponseEntity<GpSurgery> getGpSurgery(@PathVariable String gpSurgeryId) {
        Optional<GpSurgery> gpSurgery =  this.gpSurgeryService.getGpSurgery(UUID.fromString(gpSurgeryId));
        return gpSurgery.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound(). build());
    }

    @Operation(summary = "Create a new GP Surgery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "GP Created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GpSurgery.class)) }),
            @ApiResponse(responseCode = "400", description = "GP Surgery could not be created",
                    content = @Content) })
    @PostMapping(value = "/gp-surgery")
    public GpSurgery createGpSurgery(@RequestBody GpSurgery gpSurgery) {
        return this.gpSurgeryService.createGpSurgery(gpSurgery);
    }

    @Operation(summary = "Update an existing GP Surgery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GP Surgery updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GpSurgery.class)) }),
            @ApiResponse(responseCode = "400", description = "GP Surgery could not be updated",
                    content = @Content) })
    @PutMapping(value = "/gp-surgery")
    public GpSurgery updateGpSurgery(@RequestBody GpSurgery gpSurgery) {
        return this.gpSurgeryService.updateGpSurgery(gpSurgery);
    }

    @Operation(summary = "Delete an existing GP Surgery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GP Surgery deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GpSurgery.class)) }),
            @ApiResponse(responseCode = "400", description = "GP Surgery could not be deleted",
                    content = @Content) })
    @DeleteMapping(value = "/gp-surgery/{gpSurgeryId}")
    public void deleteGpSurgery(@PathVariable String gpSurgeryId) {
        this.gpSurgeryService.deleteGpSurgery(UUID.fromString(gpSurgeryId));
    }
}
