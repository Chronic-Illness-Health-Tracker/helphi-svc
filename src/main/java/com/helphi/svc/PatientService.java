package com.helphi.svc;

import com.helphi.api.HealthCondition;
import com.helphi.api.user.Patient;
import com.helphi.exception.DuplicateEntityException;
import com.helphi.exception.NotFoundException;
import com.helphi.question.api.grpc.*;
import com.helphi.repository.HealthConditionRepository;
import com.helphi.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void getPatientCheckInsForCondition(UUID userId, UUID conditionId) {
/*        var request = GetUsersResponsesForConditionRequest.newBuilder()
                .setUserId(userId.toString())
                .setConditionId(conditionId.toString())
                .build();

        GetUserResponsesReply response = this.questionSvc.getUsersResponsesForCondition(request);

        *//*TODO return response */

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

    private List<Patient> getPatientByNHSNumber(String nhsNumber){
        return this.patientRepository.findByNHSNumber(nhsNumber);
    }

    private List<Patient> getPatientByName(String name){
        return this.patientRepository.findByName(name);
    }
}
