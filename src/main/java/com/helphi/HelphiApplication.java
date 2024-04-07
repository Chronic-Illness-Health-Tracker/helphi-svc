package com.helphi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.helphi", "com.helphi.question.api.grpc"})
public class HelphiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelphiApplication.class, args);
	}

}
