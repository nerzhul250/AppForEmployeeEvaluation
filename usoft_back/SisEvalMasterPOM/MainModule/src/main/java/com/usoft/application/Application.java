package com.usoft.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

import com.usoft.security.Roles;

@SpringBootApplication(scanBasePackages = "com.usoft")
@EntityScan( basePackages = {"com.usoft.model"} )
public class Application {

	public static void main(String[] args) {
	    ConfigurableApplicationContext cac = SpringApplication.run(Application.class, args);
	}

}
