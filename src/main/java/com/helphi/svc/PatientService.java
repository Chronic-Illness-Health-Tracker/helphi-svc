package com.helphi.svc;

import com.helphi.api.HealthCondition;
import com.helphi.api.user.Patient;
import com.helphi.api.user.User;
import com.helphi.exception.DuplicateEntityException;
import com.helphi.exception.NotFoundException;
import com.helphi.question.api.PatientStatus;
import com.helphi.question.api.UserResponse;
import com.helphi.question.api.grpc.GetUserStatusRequest;
import com.helphi.question.api.grpc.ListPatientStatusRequest;
import com.helphi.question.api.grpc.PatientStatusList;
import com.helphi.question.api.grpc.QuestionServiceGrpc;
import com.helphi.question.api.mapper.PatientStatusMapper;
import com.helphi.question.api.mapper.UserResponseMapper;
import com.helphi.repository.HealthConditionRepository;
import com.helphi.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PatientService {

    private final PatientRepository patientRepository;
    private final HealthConditionService healthConditionService;
    private final QuestionServiceGrpc.QuestionServiceBlockingStub questionSvc;

    @Autowired
    public PatientService(PatientRepository patientRepository,
                          HealthConditionService healthConditionService,
                          QuestionServiceGrpc.QuestionServiceBlockingStub questionSvc)
    {
        this.patientRepository = patientRepository;
        this.healthConditionService = healthConditionService;
        this.questionSvc = questionSvc;
    }
    
    public Optional<Patient> getPatient(UUID patientId) throws IllegalArgumentException {
        return this.patientRepository.findById(patientId);
    }

    public Patient createPatient(Patient patient) throws IllegalArgumentException {
        return this.patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) throws IllegalArgumentException {
        return this.patientRepository.findById(patient.getId())
            .map(existingPatient -> this.patientRepository.save(patient))
            .orElseThrow(() -> new NotFoundException(String.format("Patient with id %s cannot be found", patient.getId().toString())));
    }

    public void deletePatient(UUID patientId) throws NotFoundException {
        try {
            this.patientRepository.deleteById(patientId);
        } catch (IllegalArgumentException ex) {
            throw new NotFoundException(String.format("Patient with id %s cannot be found", patientId.toString()));
        }
    }

    public Patient addHealthCondition(UUID patientId, HealthCondition healthCondition) {
        return findAndAddCondition(patientId, healthCondition);
    }

    public Patient addHealthConditionById(UUID patientId, UUID healthConditionId) {
        Optional<HealthCondition> healthCondition = this.healthConditionService.getCondition(healthConditionId);

        healthCondition.orElseThrow(() -> new NotFoundException(String.format("Condition with id %s does not exist", healthConditionId.toString())));
        return findAndAddCondition(patientId, healthCondition.get());
    }

    public Patient deleteHealthConditionById(UUID patientId, UUID healthConditionId) {
        this.healthConditionService.getCondition(healthConditionId)
                .orElseThrow(() -> new NotFoundException(String.format("Condition with id %s does not exist", healthConditionId.toString())));

        return this.patientRepository.findById(patientId)
            .map(patient -> {
                List<HealthCondition> patientConditions = patient.getConditions();
                
                HealthCondition conditionToRemove = patientConditions.stream()
                    .filter(condition -> condition.getId().equals(healthConditionId)).findFirst()
                    .orElseThrow(() -> new NotFoundException(String.format("Patient is not associated with condition with id %s", healthConditionId)));

                patientConditions.remove(conditionToRemove);
                patient.setConditions(patientConditions);

                return this.patientRepository.save(patient);
            })
            .orElseThrow(() -> new NotFoundException(String.format("Patient with id %s could not be found", patientId)));
    }

    public List<Patient> getPatientsInCondition(UUID healthConditionId) {
        return this.patientRepository.findByHealthConditionId(healthConditionId);

    }

    private Patient findAndAddCondition(UUID patientId, HealthCondition healthCondition) {
        return this.patientRepository.findById(patientId)
                .map(patient ->  {
                    if(patient.getConditions().contains(healthCondition)) {
                        String message = String.format("%s %s is already a member of the %s group ",
                                patient.getForename(), patient.getLastname(), healthCondition.getName());
                        String details = String.format("Patient %s is already associated with condition %s ", patient.getId(),
                                healthCondition.getId());
                        throw new DuplicateEntityException(message, details);
                    }
                    patient.getConditions().add(healthCondition);
                    return this.patientRepository.save(patient);
                })
                .orElseThrow(() -> new NotFoundException(String.format("Patient with ID %s could not be found", patientId.toString())));
    }

    public List<Patient> getPatients() {
        return this.patientRepository.findAll();

    }

    public List<Patient> getPatients(String searchValue) {
        String isANumberTestStr = searchValue.replaceAll("\\s", "");
        try {
            Integer.parseInt(isANumberTestStr);
            return getPatientByNHSNumber(isANumberTestStr);
        }
        catch(Exception e) {
            return getPatientByName(searchValue);
        }
    }

    public UserResponse addAnswerToQuestion(UserResponse response) {
        Optional<Patient> patient = this.getPatient(UUID.fromString(response.getUserId()));
        if(patient.isEmpty()){
            throw new NotFoundException(String.format("Patient with ID: %s does not exist", response.getUserId()));
        }
        Optional<HealthCondition> condition = this.healthConditionService.getCondition(UUID.fromString(response.getConditionId()));

        if(condition.isEmpty()) {
            throw new NotFoundException(String.format("Condition with ID: %s cannot be found", response.getConditionId()));
        }

        UserResponseMapper mapper = new UserResponseMapper();

        com.helphi.question.api.grpc.UserResponse createdResponse = this.questionSvc.addUserResponse(mapper.mapToGrpc(response));
        return mapper.mapFromGrpc(createdResponse);
    }

    public List<PatientStatus> getRecentPatientStatus(UUID patientId, UUID conditionId) {
        var request = ListPatientStatusRequest.newBuilder()
                .setPatientId(patientId.toString())
                .setConditionId(conditionId.toString())
                .setDays(30)
                .build();
        PatientStatusList grpcStatuses = this.questionSvc.getRecentPatientStatus(request);

        PatientStatusMapper mapper = new PatientStatusMapper();

        List<PatientStatus> patientStatuses = new ArrayList<>();
        for (com.helphi.question.api.grpc.PatientStatus status : grpcStatuses.getStatusesList()) {
            patientStatuses.add(mapper.mapFromGrpc(status));
        }

        return patientStatuses;
    }

    public PatientStatus getMostRecentPatientStatus(UUID patientId, UUID conditionId) {
        var request = GetUserStatusRequest.newBuilder()
                .setUserId(patientId.toString())
                .setConditionId(conditionId.toString())
                .build();
        com.helphi.question.api.grpc.PatientStatus grpcStatus = this.questionSvc.getCurrentPatientStatus(request);

        PatientStatusMapper mapper = new PatientStatusMapper();

        return mapper.mapFromGrpc(grpcStatus);
    }



    private List<Patient> getPatientByNHSNumber(String nhsNumber){
        return this.patientRepository.findByNHSNumber(nhsNumber);
    }

    private List<Patient> getPatientByName(String name){
        return this.patientRepository.findByName(name);
    }


}
