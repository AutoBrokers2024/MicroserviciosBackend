package com.autoBrokers.contract_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@ImportAutoConfiguration
public class ContractServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractServiceApplication.class, args);
	}

}
