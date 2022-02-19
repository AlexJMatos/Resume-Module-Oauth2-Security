package com.resume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ResumeModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeModuleApplication.class, args);
	}

	// http://localhost:8080/swagger-ui.html
	@Bean
	public OpenAPI openAPIConfig() {
		return new OpenAPI().info(apiInfo());
	}

	public Info apiInfo() {
		Info info = new Info();
		info.setTitle("Resume API");
		info.setDescription("Microservice to handle resumes. Includes basic information, educational background, job experience, technologies and skills");
		info.setVersion("1.0");
		Contact contact = new Contact();
		contact.setEmail("info@theksquaregroup.com");
		contact.setName("The Ksquare Group");
		contact.setUrl("https://itk.mx/");
		info.setContact(contact);
		return info;
	}
}
