package com.helphi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.helphi", "com.helphi.question.api.grpc.QuestionServiceGrpc"})

public class HelphiSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelphiSvcApplication.class, args);
	}

}
