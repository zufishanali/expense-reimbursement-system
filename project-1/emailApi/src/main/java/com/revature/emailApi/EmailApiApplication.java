package com.revature.emailApi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EmailApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> System.out.println("Hello World");
	}
}
