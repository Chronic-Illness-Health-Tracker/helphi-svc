package com.helphi.controller;

import com.helphi.svc.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    private PatientService svc;

    @GetMapping(value = "/test")
    public void test() {
        this.svc.getPatientCheckIns();
    }
}
