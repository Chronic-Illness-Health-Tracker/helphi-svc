package com.helphi.controller;

import com.helphi.api.user.User;
import com.helphi.svc.UserService;
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
    @GetMapping(name = "/user/{userId}")
    public User getUser(@PathVariable(value = "userId") String userId) {
        return this.userService.getUser(UUID.fromString(userId));
    }
}
