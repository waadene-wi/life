package com.wy.life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeApplication.class, args);
	}
}
