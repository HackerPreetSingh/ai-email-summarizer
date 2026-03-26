package com.example.emailsummarizer.controller;

import com.example.emailsummarizer.dto.AiResponse;
import com.example.emailsummarizer.dto.EmailMeta;
import com.example.emailsummarizer.dto.UserRequest;
import com.example.emailsummarizer.service.AiService;
import com.example.emailsummarizer.service.GmailAuthService;
import com.example.emailsummarizer.service.GmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailController {

    private final AiService aiService;
    private final GmailAuthService gmailAuthService;
    private final GmailService gmailService;

    //only runs once per system to authenticate user
    @GetMapping("/gmail/test")
    public String testGmail() throws Exception {
        gmailAuthService.getGmailService();
        return "Authenticated";
    }

    @GetMapping("/gmail/list")
    public ResponseEntity<List<EmailMeta>> listEmails() throws Exception {
        return ResponseEntity.ok(gmailService.getEmailList());
    }

    @PostMapping("/gmail/userRequest")
    public ResponseEntity<AiResponse> getEmailResponse(@RequestBody UserRequest userRequest) throws Exception {

        return ResponseEntity.ok(aiService.getEmailResponse(userRequest));
    }

    /*@PostMapping("/response")
    public ResponseEntity<?> summarize(@RequestBody EmailRequest emailRequest) {

        if (emailRequest.emailText() == null || emailRequest.emailText().isBlank()) {
            return new ResponseEntity<>("Null/Blank Email Body", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(aiService.summarize(emailRequest), HttpStatus.OK);
    }*/
}
