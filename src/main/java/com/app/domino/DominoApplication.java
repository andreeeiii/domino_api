package com.app.domino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.app.domino")
public class DominoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DominoApplication.class, args);
	}

}
