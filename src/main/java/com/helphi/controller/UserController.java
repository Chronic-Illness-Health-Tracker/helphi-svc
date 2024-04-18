package com.helphi.controller;

import com.helphi.api.user.User;
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

//    @GetMapping("/user")
//    public ResponseEntity<?> getUserAuth(@AuthenticationPrincipal OAuth2User user) {
//        if (user == null) {
//            return new ResponseEntity<>("", HttpStatus.OK);
//        } else {
//            return ResponseEntity.ok().body(user.getAttributes());
//        }
//    }


    @GetMapping(value = "/user/{userId}")
    public User getUser(@PathVariable(value = "userId") String userId) {
        return this.userService.getUser(UUID.fromString(userId));
    }

    @Operation(summary = "Get a users type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User type",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping(value = "/user/{userId}/type")
    public String getUserType(@PathVariable(value = "userId") String userId) {
        return this.userService.getUserType(userId);
    }
}
