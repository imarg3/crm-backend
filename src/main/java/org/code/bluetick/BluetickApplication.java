package org.code.bluetick;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalTime;
import java.time.ZoneId;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class BluetickApplication {
	@PostConstruct
	public void init() {
		LocalTime timeIST = LocalTime.now(ZoneId.of("Asia/Kolkata"));
		log.info("Spring boot application running in Asia/Kolkata :" + timeIST);
	}

	public static void main(String[] args) {
		SpringApplication.run(BluetickApplication.class, args);
	}
}
