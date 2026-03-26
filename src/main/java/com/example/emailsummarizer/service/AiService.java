package com.example.emailsummarizer.service;

import com.example.emailsummarizer.dto.AiResponse;
import com.example.emailsummarizer.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatModel chatModel;
    private final PromptService promptService;
    private final GmailService gmailService;

    /***
     *
     * @param userRequest
     * @return
     * value i.e. emailSummaryBucket is Cache bucket where responses will be stored
     * key is value whose repetition is checked
     */
    @Cacheable(value = "emailSummaryBucket", key = "#userRequest.id + '_' + #userRequest.type")
    public AiResponse getEmailResponse(UserRequest userRequest) throws Exception {

        String emailBody = gmailService.getEmailById(userRequest.id());

        log.info("Email Body: {}", emailBody);

        String promptText = promptService.getPrompt(userRequest.type()).formatted(emailBody);
        log.info("prompt text : {}", promptText);
        Prompt prompt = new Prompt(promptText);
        try {
            String responseText = CompletableFuture
                    .supplyAsync(() -> chatModel.call(prompt).getResult().getOutput().getText())
                    .orTimeout(100, TimeUnit.SECONDS)
                    .join();

            log.info("Response Text: {}", responseText);
            if (userRequest.type().equals("summary")) {
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
