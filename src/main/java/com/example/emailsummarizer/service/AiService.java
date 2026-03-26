package com.example.emailsummarizer.service;

import com.example.emailsummarizer.dto.AiResponse;
import com.example.emailsummarizer.dto.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AiService {

    private final ChatModel chatModel;
    private final PromptService promptService;

    public AiService(ChatModel chatModel, PromptService promptService) {
        this.chatModel = chatModel;
        this.promptService = promptService;
    }

    /***
     *
     * @param emailRequest
     * @return
     * value i.e. emailSummaryBucket is Cache bucket where responses will be stored
     * key is value whose repetition is checked
     */
    @Cacheable(value = "emailSummaryBucket", key = "#emailRequest.emailText + '_' + #emailRequest.type")
    public AiResponse summarize(EmailRequest emailRequest) {

        String promptText = promptService.getPrompt(emailRequest.type()).formatted(emailRequest.emailText());
        log.info("prompt text : {}", promptText);
        Prompt prompt = new Prompt(promptText);
        try {
            String responseText = CompletableFuture
                    .supplyAsync(() -> chatModel.call(prompt).getResult().getOutput().getText())
                    .orTimeout(10, TimeUnit.SECONDS)
                    .join();

            log.info("Response Text: {}", responseText);
            if (emailRequest.type().equals("summary")) {
                List<String> responseTextArray = Arrays.stream(responseText.split("\n")).toList();
                return new AiResponse(responseTextArray, null);
            } else {
                return new AiResponse(null, responseText);
            }
        } catch (Exception e) {
            log.error("API failed to respond. Error: {}", e.getMessage());
            throw new RuntimeException(e.getCause());
        }
    }
}
