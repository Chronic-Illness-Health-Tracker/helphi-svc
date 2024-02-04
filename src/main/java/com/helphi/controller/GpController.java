package com.helphi.controller;

import com.helphi.api.Gp;
import com.helphi.api.organisation.Organisation;
import com.helphi.svc.GpService;
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
public class GpController {

    private final GpService gpService;

    public GpController(GpService gpService) {
        this.gpService = gpService;
    }


    @Operation(summary = "Get a GP by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the GP",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Gp.class)) }),
        @ApiResponse(responseCode = "404", description = "GP not found",
            content = @Content) })
    @GetMapping(value = "/gp/{gpId}")
    public ResponseEntity<Gp> getGp(@PathVariable String gpId) {
        Optional<Gp> gp =  this.gpService.getGp(UUID.fromString(gpId));
        return gp.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new GP")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "GP Created",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Gp.class)) }),
        @ApiResponse(responseCode = "400", description = "GP could not be created",
            content = @Content) })
    @PostMapping(value = "/gp")
    public Gp createGp(@RequestBody Gp gp) {
        return this.gpService.createGp(gp);
    }

    @Operation(summary = "Update an existing GP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GP Updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gp.class)) }),
            @ApiResponse(responseCode = "400", description = "GP could not be updated",
                    content = @Content) })
    @PutMapping(value = "/gp")
    public Gp updateGp(@RequestBody Gp gp) {
        return this.gpService.updateGp(gp);
    }

    @Operation(summary = "Delete an existing GP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GP deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gp.class)) }),
            @ApiResponse(responseCode = "400", description = "GP could not be deleted",
                    content = @Content) })
    @DeleteMapping(value = "/gp/{gpId}")
    public void deleteGp(@PathVariable String gpId) {
        this.gpService.deleteGp(UUID.fromString(gpId));
    }

}
