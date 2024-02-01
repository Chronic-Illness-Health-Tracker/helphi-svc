package com.helphi;

import com.helphi.question.api.grpc.QuestionServiceGrpc;
import com.helphi.repository.HealthConditionRepository;
import com.helphi.repository.PatientRepository;
import com.helphi.svc.PatientService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@GrpcClientBean(
        clazz = QuestionServiceGrpc.QuestionServiceBlockingStub.class,
        beanName = "blockingStub",
        client = @GrpcClient("question-service")
)
public class HelphiConfiguration {

    /* IDE can incorrectly say bean cannot be autowired. Ignore */
    @Bean
    @Autowired
    PatientService petientService(PatientRepository patientRepository,
                                  HealthConditionRepository conditionRepository,
                                  QuestionServiceGrpc.QuestionServiceBlockingStub blockingStub) {
        return new PatientService(patientRepository, conditionRepository, blockingStub);
    }


}
