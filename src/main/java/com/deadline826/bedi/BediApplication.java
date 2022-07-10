package com.deadline826.bedi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BediApplication {

	public static void main(String[] args) {
		SpringApplication.run(BediApplication.class, args);
	}

}
