package org.code.bluetick.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "crm")
@Getter@Setter
public class MailProperties {
    private String greeting;
}
