package com.helphi.svc;

import com.helphi.api.user.*;
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
    public BaseUser getUser(String userId) {
        Optional<Patient> patient = this.patientService.getByUserId(userId);

        if(patient.isPresent()) {
            return patient.get();
        }

        Optional<Clinician> clinician = this.clinitianService.getByUserId(userId);

        if(clinician.isPresent()) {
            return clinician.get();
        }
        throw new NotFoundException();
    }

    public UserType getUserType(String userId) {
        Optional<Patient> patient = this.patientService.getByUserId(userId);

        if(patient.isPresent()) {
            return new UserType(patient.get().getId(), UserTypes.PATIENT);
        }

        Optional<Clinician> clinitian = this.clinitianService.getByUserId(userId);

        if(clinitian.isPresent()) {
            return new UserType(clinitian.get().getId(), UserTypes.CLINITIAN);
        }

       throw new NotFoundException(String.format("User with ID: %s does not exist",userId));
    }
}
