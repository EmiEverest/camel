package com.eip.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CamelEnterpriseIntegrationPatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelEnterpriseIntegrationPatternApplication.class, args);
	}

}
