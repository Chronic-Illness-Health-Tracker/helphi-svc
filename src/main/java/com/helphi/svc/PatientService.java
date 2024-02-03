package com.helphi.svc;

import com.helphi.api.user.Patient;
import com.helphi.question.api.grpc.*;
import com.helphi.repository.HealthConditionRepository;
import com.helphi.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public class PatientService {

    private final PatientRepository patientRepository;
    private final HealthConditionRepository conditionRepository;
    private final QuestionServiceGrpc.QuestionServiceBlockingStub questionSvc;

    @Autowired
    public PatientService(PatientRepository patientRepository,
                          HealthConditionRepository conditionRepository,
                          QuestionServiceGrpc.QuestionServiceBlockingStub questionSvc)
    {
        this.patientRepository = patientRepository;
        this.conditionRepository = conditionRepository;
        this.questionSvc = questionSvc;
    }
    
    public Optional<Patient> getPatient(UUID patientId) throws IllegalArgumentException {
        return this.patientRepository.findById(patientId);
    }

    public Patient createPatient(Patient patient) throws IllegalArgumentException {
        return this.patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) throws IllegalArgumentException, EntityNotFoundException {
        return this.patientRepository.save(patient);
    }

    public void deletePatient(UUID patientId) throws IllegalArgumentException {
        this.patientRepository.deleteById(patientId);
    }

    public void getPatientConditions(UUID userId) {
    }

    public void addConditionToPatient(UUID conditionId) {
    }

    public void getPatientsInCondition() {

    }

    public void getPatientCheckInsForCondition(UUID userId, UUID conditionId) {
        var request = GetUsersResponsesForConditionRequest.newBuilder()
                .setUserId(userId.toString())
                .setConditionId(conditionId.toString())
                .build();

        GetUserResponsesReply response = this.questionSvc.getUsersResponsesForCondition(request);

        /*TODO return response */

    }


}
