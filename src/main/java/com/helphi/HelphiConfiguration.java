package com.helphi;

import com.helphi.question.api.grpc.QuestionServiceGrpc;
import com.helphi.repository.HealthConditionRepository;
import com.helphi.repository.OrganisationRepository;
import com.helphi.repository.PatientRepository;
import com.helphi.svc.AddressService;
import com.helphi.svc.HealthConditionService;
import com.helphi.svc.OrganisationService;
import com.helphi.svc.PatientService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    PatientService patientService(PatientRepository patientRepository,
                                  HealthConditionService healthConditionService,
                                  QuestionServiceGrpc.QuestionServiceBlockingStub blockingStub) {
        return new PatientService(patientRepository, healthConditionService, blockingStub);
    }

    @Bean
    @Autowired
    HealthConditionService healthConditionService(HealthConditionRepository healthConditionRepository,
                                  QuestionServiceGrpc.QuestionServiceBlockingStub blockingStub) {
        return new HealthConditionService(healthConditionRepository, blockingStub);
    }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods(HttpMethod.GET.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.DELETE.name())
                        .allowedHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION);
            }
        };
    }


}
