package com.aomsir.jewixapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class JewixApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JewixApiApplication.class, args);
	}

}
