package org.code.bluetick;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalTime;
import java.time.ZoneId;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class BluetickApplication {
	@Value("${spring.mail.host}")
	private static String mailHost;

	@PostConstruct
	public void init() {
		LocalTime timeIST = LocalTime.now(ZoneId.of("Asia/Kolkata"));
		log.info("Spring boot application running in Asia/Kolkata :" + timeIST);
	}

	public static void main(String[] args) {
		System.out.println("Mail Host - " + mailHost);
		System.out.println(System.getenv(("MAIL_USERNAME")));
		System.out.println(System.getenv(("MAIL_PASSWORD")));
		System.out.println(System.getenv(("DB_USERNAME")));
		System.out.println(System.getenv(("DB_PASSWORD")));
		ConfigurableApplicationContext ctx = SpringApplication.run(BluetickApplication.class, args);
		System.out.println(ctx.getEnvironment().getProperty("mail.host"));
	}
}
