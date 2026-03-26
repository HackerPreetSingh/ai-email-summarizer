package com.example.emailsummarizer.controller;

import com.example.emailsummarizer.dto.EmailRequest;
import com.example.emailsummarizer.service.AiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class EmailController {

    private final AiService aiService;

    public EmailController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/response")
    public ResponseEntity<?> summarize(@RequestBody EmailRequest emailRequest) {

        if (emailRequest.emailText() == null || emailRequest.emailText().isBlank()) {
            return new ResponseEntity<>("Null/Blank Email Body", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(aiService.summarize(emailRequest), HttpStatus.OK);
    }
}
