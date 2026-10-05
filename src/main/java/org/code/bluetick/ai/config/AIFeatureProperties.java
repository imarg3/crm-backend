package org.code.bluetick.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "feature.ai")
public record AIFeatureProperties(boolean enabled) {}
