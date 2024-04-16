package com.helphi.svc;

import com.helphi.api.HealthCondition;
import com.helphi.api.organisation.Organisation;
import com.helphi.exception.ForeignKeyConstraintException;
import com.helphi.exception.NotFoundException;
import com.helphi.question.api.Question;
import com.helphi.question.api.grpc.*;
import com.helphi.question.api.mapper.AnswerMapper;
import com.helphi.question.api.mapper.CheckInMapper;
import com.helphi.question.api.mapper.QuestionMapper;
import com.helphi.repository.HealthConditionRepository;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HealthConditionService {

    private final HealthConditionRepository conditionRepository;
    private final QuestionServiceGrpc.QuestionServiceBlockingStub questionSvc;

    @Autowired
    public HealthConditionService(HealthConditionRepository conditionRepository,
                                  QuestionServiceGrpc.QuestionServiceBlockingStub questionSvc) {
        this.conditionRepository = conditionRepository;
        this.questionSvc = questionSvc;
    }

    public Optional<HealthCondition> getCondition(UUID conditionId) {
        return this.conditionRepository.findById(conditionId);
    }

    public HealthCondition createCondition(HealthCondition condition) {
        HealthCondition addedCondition = null;

        try {
            addedCondition = this.conditionRepository.save(condition);
        } catch (DataIntegrityViolationException ex) {
            if(ex.getMessage().contains("\"health_condition\" violates foreign key constraint \"fk_organisation\"")){
                throw new ForeignKeyConstraintException("Invalid Organisation");
            } else {
                throw ex;
            }
        }

        com.helphi.question.api.ConditionCheckIn checkIn = new com.helphi.question.api.ConditionCheckIn(
                addedCondition.getId().toString(), 0,0);

        this.AddCheckIn(checkIn);

        return addedCondition;
    }

    public HealthCondition updateCondition(HealthCondition condition) {
        return this.conditionRepository.save(condition);
    }
    public void deleteCondition(UUID conditionId) {
        this.conditionRepository.deleteById(conditionId);
    }

    public List<HealthCondition> listHealthConditionsByName(String healthConditionName) {
        if(healthConditionName == null) {
            return this.conditionRepository.findAll();
        } else {
            return this.conditionRepository.findByName(healthConditionName.toLowerCase());
        }
    }

    public Question addQuestionToHealthCondition(UUID conditionId, Question question) {

        Optional<HealthCondition> condition = this.conditionRepository.findById(conditionId);

        if(condition.isEmpty()){
            throw new NotFoundException(String.format("Condition with id: %s does not exist", conditionId.toString()));
        }

        AnswerMapper mapper = new AnswerMapper();
        List<Answer> grpcAnswers = new ArrayList<>();
        for(com.helphi.question.api.Answer answer : question.getPossibleAnswers()) {
            grpcAnswers.add(mapper.mapToGrpc(answer));
        }
        QuestionRequest request = QuestionRequest.newBuilder()
                .setConditionId(question.getConditionId())
                .setQuestionType(String.valueOf(question.getQuestionType()))
                .addAllPossibleAnswers(grpcAnswers)
                .addAllAnswerScoreRange(question.getAnswerScoreRange())
                .build();

        com.helphi.question.api.grpc.Question createdQuestion = this.questionSvc.addQuestion(request);

        if(createdQuestion == null) {
            throw new IllegalArgumentException("Could not create question");
        }

        QuestionMapper questionMapper = new QuestionMapper();
        return questionMapper.mapFromGrpc(createdQuestion);
    }

    public void AddCheckIn(com.helphi.question.api.ConditionCheckIn checkIn) {

        Optional<HealthCondition> condition = this.getCondition(UUID.fromString(checkIn.getConditionId()));

        if(condition.isEmpty()) {
            throw new IllegalArgumentException(String.format("Condition with ID: %s could not be found", checkIn.getConditionId()));
        }

        ConditionCheckIn checkInGrpc = ConditionCheckIn.newBuilder()
                .setConditionId(checkIn.getConditionId())
                .setSubclinicalScore(checkIn.getSubclinicalScore())
                .setUnwellScore(checkIn.getUnwellScore())
                .build();

        this.questionSvc.addCheckIn(checkInGrpc);
    }

    public void updateCheckIn(String conditionId, com.helphi.question.api.ConditionCheckIn checkIn) {

        if(!this.validateCheckIn(checkIn, conditionId)) {
            throw new IllegalArgumentException("Condition ID is invalid");
        }

        ConditionCheckIn checkInGrpc = ConditionCheckIn.newBuilder()
                .setConditionId(checkIn.getConditionId())
                .setSubclinicalScore(checkIn.getSubclinicalScore())
                .setUnwellScore(checkIn.getUnwellScore())
                .build();

        this.questionSvc.updateCheckIn(checkInGrpc);
    }

    public List<Question> listConditionQuestions(UUID conditionId) {

        var request = GetConditionQuestionsRequest.newBuilder()
                .setConditionId(conditionId.toString())
                .build();

        GetConditionQuestionsReply reply = this.questionSvc.getConditionQuestions(request);

        List<com.helphi.question.api.grpc.Question> questionsGrpc = reply.getQuestionsList();

        QuestionMapper mapper = new QuestionMapper();

        List<Question> questions = new ArrayList<>();
        for (com.helphi.question.api.grpc.Question question : questionsGrpc) {
            questions.add(mapper.mapFromGrpc(question));
        }

        return questions;
    }

    public void addQuestionsToHealthCondition(UUID conditionId, List<Question> questions) {

        for(Question question: questions) {
            if(question.getQuestionId() == 0) {
                question.setConditionId(conditionId.toString());
                this.addQuestionToHealthCondition(conditionId, question);
            } else {
                this.updateQuestion(question);
            }
        }
    }

    public void updateQuestion(Question question) {
        QuestionMapper mapper = new QuestionMapper();
        this.questionSvc.updateQuestion(mapper.mapToGrpc(question));
    }

    public com.helphi.question.api.ConditionCheckIn getCheckIn(UUID conditionId) {
        GetConditionCheckInRequest request = GetConditionCheckInRequest.newBuilder()
                .setConditionId(conditionId.toString())
                .build();

        ConditionCheckIn grpcCheckIn = this.questionSvc.getCheckIn(request);

        if(grpcCheckIn.getConditionId().isBlank()) {
           throw new NotFoundException();
        }

        CheckInMapper mapper = new CheckInMapper();
        return mapper.mapFromGrpc(grpcCheckIn);
    }

    private boolean validateCheckIn(com.helphi.question.api.ConditionCheckIn checkIn, String conditionId) {

        if(!Objects.equals(conditionId, checkIn.getConditionId())) {
            return false;
        }

        Optional<HealthCondition> condition = this.getCondition(UUID.fromString(checkIn.getConditionId()));
        return condition.isPresent();
    }

}
