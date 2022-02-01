package com.example.koerber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
@EnableMongoRepositories
@SpringBootApplication
public class KoerberApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoerberApplication.class, args);
	}

}
