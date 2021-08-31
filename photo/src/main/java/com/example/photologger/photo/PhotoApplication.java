package com.example.photologger.photo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PhotoApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="
		+ "classpath:application.properties,"
		+ "classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(PhotoApplication.class)
			.properties(APPLICATION_LOCATIONS)
			.run(args);
	}
}
