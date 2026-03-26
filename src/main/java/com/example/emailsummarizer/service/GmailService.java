package com.example.emailsummarizer.service;

import com.example.emailsummarizer.dto.EmailMeta;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GmailService {

    private final GmailAuthService authService;

    public List<EmailMeta> getEmailList() throws Exception {

        Gmail gmail = authService.getGmailService();

        ListMessagesResponse response =
                gmail.users().messages()
                        .list("me")
                        .setMaxResults(5L)
//                        .setQ("is:unread")
                        .execute();

        List<EmailMeta> result = new ArrayList<>();

        if (response.getMessages() == null) return result;

        for (Message msg : response.getMessages()) {

            Message fullMessage = gmail.users().messages()
                    .get("me", msg.getId())
                    .execute();

            result.add(new EmailMeta(
                    msg.getId(),
                    fullMessage.getSnippet()
            ));
        }

        return result;
    }

    public String getEmailById(String id) throws Exception {

        Gmail gmail = authService.getGmailService();

        Message message = gmail.users().messages()
                .get("me", id)
                .setFormat("full")
                .execute();

        return extractText(message.getPayload());
    }

    private String extractText(MessagePart payload) {

        if (payload == null) return "";

        Queue<MessagePart> queue = new LinkedList<>();
        queue.add(payload);

        String htmlFallback = "";

        while (!queue.isEmpty()) {
            MessagePart part = queue.poll();

            String mimeType = part.getMimeType();

            // Prefer plain text
            if ("text/plain".equalsIgnoreCase(mimeType)) {
                return decode(part.getBody().getData());
            }

            // Store html as fallback
            if ("text/html".equalsIgnoreCase(mimeType) && htmlFallback.isEmpty()) {
                htmlFallback = decode(part.getBody().getData());
            }

            // Traverse children
            if (part.getParts() != null) {
                queue.addAll(part.getParts());
            }
        }

        return htmlFallback;
    }

    private String decode(String data) {
        if (data == null) return "";
        return new String(Base64.getUrlDecoder().decode(data));
    }
}
