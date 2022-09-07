package com.sg.superherofinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("module-service")
public class SuperherofinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperherofinderApplication.class, args);
	}

}
