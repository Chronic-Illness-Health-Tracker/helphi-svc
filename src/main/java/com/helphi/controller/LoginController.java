package com.helphi.controller;

import com.helphi.api.login.Request;
import com.helphi.api.login.Response;
import com.helphi.svc.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // @GetMapping(name = "/login")
    // public void login(@RequestBody Request loginRequest) {
    // }

    // @GetMapping(name = "/logout")
    // public void logout() {

    // }
}
