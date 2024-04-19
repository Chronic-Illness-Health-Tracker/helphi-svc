package com.helphi.controller;

import com.helphi.api.user.BaseUser;
import com.helphi.api.user.User;
import com.helphi.api.user.UserType;
import com.helphi.api.user.UserTypes;
import com.helphi.svc.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }


    @Operation(summary = "Get a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BaseUser.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping(value = "/user/{userId}")
    public BaseUser getUser(@PathVariable(value = "userId") String userId) {
        return this.userService.getUser(userId);
    }

    @Operation(summary = "Get a users type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User type",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserType.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping(value = "/user/{userId}/type")
    public UserType getUserType(@PathVariable(value = "userId") String userId) {
        return this.userService.getUserType(userId);
    }
}
