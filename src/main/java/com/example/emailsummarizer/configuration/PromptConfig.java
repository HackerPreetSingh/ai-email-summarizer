package com.example.emailsummarizer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ai.prompts")
@Getter
@Setter
public class PromptConfig {
    private String summary;
    private String reply;
}
