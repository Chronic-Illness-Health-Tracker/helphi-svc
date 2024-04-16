package com.helphi.svc;

import com.helphi.api.user.Clinician;
import com.helphi.api.user.Patient;
import com.helphi.api.user.User;
import com.helphi.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final PatientService patientService;
    private final ClinicianService clinitianService;


    @Autowired
    public UserService(PatientService patientService, ClinicianService clinicianService) {
        this.patientService = patientService;
        this.clinitianService = clinicianService;
    }
    public User getUser(UUID userId) {
        return null;
    }

    public String getUserType(String userId) {
        Optional<Patient> patient = this.patientService.getByUserId(userId);

        if(patient.isPresent()) {
            return "patient";
        }

        Optional<Clinician> clinitian = this.clinitianService.getByUserId(userId);

        if(clinitian.isPresent()) {
            return "clinician";
        }

       throw new NotFoundException(String.format("User with ID: %s does not exist",userId));
    }
}
