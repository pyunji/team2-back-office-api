package com.mycompany.webapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Team2BackOfficeApiApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location=" 
			+ "classpath:application.properties,"
			+ "classpath:aws.properties";

	public static void main(String[] args) {
		new SpringApplicationBuilder(Team2BackOfficeApiApplication.class)
			.properties(APPLICATION_LOCATIONS)
			.run(args);
	}

}
