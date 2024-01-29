package com.helphi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.helphi", "com.helphi.question.api.grpc.QuestionServiceGrpc"})

public class HelphiSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelphiSvcApplication.class, args);
	}

}
