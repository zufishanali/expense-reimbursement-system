package com.revature.reimbursementApi;

import com.revature.reimbursementApi.entities.*;
import com.revature.reimbursementApi.repositories.ReimbursementRepository;
import com.revature.reimbursementApi.repositories.UserRepository;
import com.revature.reimbursementApi.services.ReimbursementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootApplication
public class ReimbursementApiApplication {

	final Logger logger = LoggerFactory.getLogger(ReimbursementApiApplication.class);

	@Autowired
	private ReimbursementService reimbursementService;

	public static void main(String[] args) {
		SpringApplication.run(ReimbursementApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> System.out.println("Hello World");
	}

//	@Bean
//	public RestTemplate restTemplate(RestTemplateBuilder builder){
//		return builder.build();
//	}

}
