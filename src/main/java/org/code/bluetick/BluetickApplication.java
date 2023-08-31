package org.code.bluetick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BluetickApplication {

	public static void main(String[] args) {
		System.out.println(System.getenv(("MAIL_USERNAME")));
		System.out.println(System.getenv(("MAIL_PASSWORD")));
		System.out.println(System.getenv(("DB_USERNAME")));
		System.out.println(System.getenv(("DB_PASSWORD")));
		SpringApplication.run(BluetickApplication.class, args);
	}

}
