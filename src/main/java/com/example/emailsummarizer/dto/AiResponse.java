package com.example.emailsummarizer.dto;

import java.util.List;

public record AiResponse(List<String> summary, String emailResponse) {
}
