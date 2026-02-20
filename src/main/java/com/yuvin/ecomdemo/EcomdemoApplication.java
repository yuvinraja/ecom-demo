package com.yuvin.ecomdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
public class EcomdemoApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./").ignoreIfMalformed().ignoreIfMissing().load();

		if (dotenv.get("DB_URL") != null) {
			System.setProperty("DB_URL", dotenv.get("DB_URL"));
		}
		if (dotenv.get("DB_USERNAME") != null) {
			System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		}
		if (dotenv.get("DB_PASSWORD") != null) {
			System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		}

		SpringApplication.run(EcomdemoApplication.class, args);
	}

}
