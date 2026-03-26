package com.example.emailsummarizer.service;

import com.example.emailsummarizer.configuration.PromptConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PromptService {

    private final PromptConfig promptConfig;

    public PromptService(PromptConfig promptConfig) {
        this.promptConfig = promptConfig;
    }

    @Cacheable(value = "prompts", key = "#type")
    public String getPrompt(String type) {
        // later fetch it from db

        log.info("Prompt Service Hit");

        return switch (type) {
            /*case "summary" -> """
                You are a Email Summarizer. Summarize it in 3-4 pointers as below. Example:
                1. Point 1
                2. Point 2
                
                Do not add acknowledgment or explanation or any extra text.
                
                Email body:
                %s
                """;
            case "reply" -> """
                You are a Email Replier. Draft a proper email reply for this email body.
                
                Do not add acknowledgment or explanation or any extra text like - Email reply and all. Just your response.
                
                Email body:
                %s
                """;*/
            case "summary" -> promptConfig.getSummary();
            case "reply" -> promptConfig.getReply();
            default -> throw new IllegalArgumentException("Illegal Prompt type");
        };
    }
}
