package com.example.finalAssignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinalAssignmentApplication  {

	public static void main(String[] args) {
		SpringApplication.run(FinalAssignmentApplication.class, args);
	}


}
