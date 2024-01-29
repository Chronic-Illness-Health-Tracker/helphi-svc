package com.helphi.svc;

import com.helphi.api.user.Patient;
import com.helphi.question.api.grpc.GetQuestionRequest;
import com.helphi.question.api.grpc.Question;
import com.helphi.question.api.grpc.QuestionServiceGrpc;
import com.helphi.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

   // private final PatientRepository patientRepository;
    @GrpcClient("question-service")
    private QuestionServiceGrpc.QuestionServiceBlockingStub questionSvc;

    //@Autowired
   // public PatientService(PatientRepository patientRepository, QuestionServiceGrpc.QuestionServiceBlockingStub questionSvc) {
    //    this.patientRepository = patientRepository;
   //     this.questionSvc = questionSvc;
   // }
    
    public void getPatient(UUID patientId) throws IllegalArgumentException {

      //  return this.patientRepository.findById(patientId);
    }

    public void addPatient(Patient patient) throws IllegalArgumentException {
       // return this.patientRepository.save(patient);
    }

    public void updatePatient(Patient patient) throws IllegalArgumentException, EntityNotFoundException {
        //return this.patientRepository.save(patient);
    }

    public void deletePatient(UUID patientId) throws EntityNotFoundException {
       // this.patientRepository.deleteById(patientId);
    }

    public void getPatientConditions() {

    }

    public void addConditionToPatient() {

    }

    public void getPatientsInCondition() {

    }

    public void getPatientCheckIns() {
        System.out.println("CHECKING IN");
        GetQuestionRequest request = GetQuestionRequest.newBuilder().setConditionId("CONDITION_ID").build();
        Question result = this.questionSvc.getQuestion(request);

        long id = result.getQuestionId();
        System.out.println(id);

    }


}
