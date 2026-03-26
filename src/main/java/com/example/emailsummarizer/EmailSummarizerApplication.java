package com.example.emailsummarizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmailSummarizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSummarizerApplication.class, args);
	}

}
