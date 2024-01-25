package com.helphi.helphisvc.svc;

import com.helphi.api.user.Patient;
import com.helphi.helphisvc.dto.PatientDTO;
import com.helphi.helphisvc.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    
    public void getPatient(String patientId) {
    }

    public void addPatient(Patient patient) {



    }

    public void updatePatient() {

    }

    public void deletePatient() {

    }

    public void getPatientConditions() {

    }

    public void addConditionToPatient() {

    }

    public void getPatientsInCondition() {

    }

    public void getPatientCheckIns() {

    }


}
