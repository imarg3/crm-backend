package org.code.bluetick;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class BluetickApplication {
	
	private final Environment environment;
	
	public BluetickApplication(Environment environment) {
		this.environment = environment;
	}

	@PostConstruct
	public void init() {
		// Set application context in MDC for structured logging
		MDC.put("application", "bluetick-crm");
		MDC.put("version", getClass().getPackage().getImplementationVersion() != null ? 
				getClass().getPackage().getImplementationVersion() : "development");
		
		LocalDateTime timeIST = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
		log.info("Application starting at: {}", timeIST);
		log.info("Active profiles: {}", Arrays.toString(environment.getActiveProfiles()));
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		try {
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			String serverPort = environment.getProperty("server.port", "8080");
			String contextPath = environment.getProperty("server.servlet.context-path", "");
			
			log.info("Application started successfully");
			log.info("Local URL: http://localhost:{}{}", serverPort, contextPath);
			log.info("External URL: http://{}:{}{}", hostAddress, serverPort, contextPath);
			log.info("API Documentation: http://localhost:{}{}/swagger-ui-custom.html", serverPort, contextPath);
			log.info("Actuator Health: http://localhost:{}{}/actuator/health", serverPort, contextPath);
		} catch (UnknownHostException e) {
			log.warn("Unable to determine host address: {}", e.getMessage());
		}
	}

	public static void main(String[] args) {
		System.setProperty("spring.application.name", "bluetick-crm");
		
		log.info("Starting Bluetick CRM Application...");
		SpringApplication.run(BluetickApplication.class, args);
	}
}
