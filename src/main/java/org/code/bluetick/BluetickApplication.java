package org.code.bluetick;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Properties;

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
		logGitInfo();
		System.out.println(System.getenv(("MAIL_USERNAME")));
		System.out.println(System.getenv(("MAIL_PASSWORD")));
		System.out.println(System.getenv(("DB_USERNAME")));
		System.out.println(System.getenv(("DB_PASSWORD")));
		SpringApplication.run(BluetickApplication.class, args);
	}

	public static void logGitInfo() {
		Resource resource = new ClassPathResource("/git.properties");
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);

			String logMessage = String.format(
					"\nGit Commit Info:" +
							"\n============================================================================================" +
							"\nCommit ID:  %s" +
							"\nBuild Time: %s" +
							"\nUser:       %s" +
							"\nMessage:    %s" +
							"\n============================================================================================"
					,
					props.getProperty("git.commit.id"),
					props.getProperty("git.build.time"),
					props.getProperty("git.commit.user.name"),
					props.getProperty("git.commit.message.full"));

			log.info(logMessage);
		} catch (IOException e) {
			log.warn("git.properties not found!");
		}
	}

}
