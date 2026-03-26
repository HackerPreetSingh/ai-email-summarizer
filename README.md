# 🚀 AI Email Assistant (Spring Boot + Gmail + Gemini)

A production-style backend system that integrates **Gmail API + Generative AI (LLM)** to intelligently process emails.

It supports:
- 📌 Email Summarization
- ✉️ Reply Generation
- 📬 Gmail Email Fetching
- 🤖 AI-based Processing of Selected Emails

---

## 🧠 Overview

This project demonstrates how to build a real-world AI-powered backend system.

It combines:
- Gmail API (OAuth2-based email access)
- Large Language Models (Gemini)
- Prompt engineering
- Clean backend architecture

Unlike simple AI demos, this system:
- Fetches real emails from Gmail
- Extracts full email body using MIME traversal
- Processes selected emails using AI
- Returns structured responses

---

## ⚙️ Tech Stack

- **Java (Spring Boot)**
- **Spring AI (Gemini Integration)**
- **Google Gmail API**
- **OAuth2 Authentication**
- **Spring Cache**
- **REST APIs**
- **Lombok**

---

## 🔐 Configuration

### AI Configuration

```yaml
spring:
  ai:
    google:
      genai:
        api-key: YOUR_API_KEY
        chat:
          options:
            model: gemini-2.5-flash
```

### Prompt Configuration (Externalized)

```yaml
ai:
  prompts:
    summary: |
      You are a Email Summarizer. Summarize it in 3-4 pointers.

      Do not add any extra text.

      Email body:
      %s

    reply: |
      You are a Email Replier. Draft a proper email reply.

      Do not add any extra explanation.

      Email body:
      %s
```
---

### Gmail OAuth Setup

1.	Enable Gmail API in Google Cloud Console 
2. Create OAuth Client (Desktop App)
3.	Download credentials
4.	Place file in: src/main/resources/credentials.json

---

## 🔄 How It Works

Flow:
1.	User authenticates with Gmail (OAuth2)
2.	Backend fetches email metadata
3.	User selects an email (via ID)
4.	System extracts full email body from MIME structure
5.	Prompt is dynamically loaded from config
6.	AI processes the request
7.	Structured response is returned

---

## 📬 API Endpoints

### 🔹 Authenticate Gmail (one-time)

```code
GET /api/gmail/test
```

### 🔹 Fetch Emails

```code
GET /api/gmail/list
```
    
#### Response

```json
[
  {
    "id": "18c...",
    "snippet": "Hi team, please send..."
  }
]
```
---

### 🔹 Process Email (Summary / Reply)

```code
POST /api/gmail/userRequest
```

#### Request 1

```json
{
  "id": "emailId",
  "type": "summary"
}
```

#### Response 1

```json
{
  "summary": [
    "Point 1",
    "Point 2"
  ],
  "emailResponse": null
}
```

#### Request 2

```json
{
  "id": "emailId",
  "type": "reply"
}
```

#### Response 2

```json
{
  "summary": null,
  "emailResponse": "Generated reply..."
}
```
---

## 🧩 Email Parsing Logic

Gmail returns emails as **MIME multipart structures (tree format)**.

This project:
•	Traverses the entire structure (BFS)
•	Extracts the first text/plain content
•	Falls back to text/html if needed

This ensures accurate extraction even for complex emails with:
•	attachments
•	inline images
•	nested structures

---

## ⚡ Performance Optimizations

*	✅ Caching (AI responses + prompts)
*	✅ Externalized prompts (no redeploy needed)
*	✅ Timeout handling for AI calls
*	✅ Input control to reduce token usage

---

## 🧪 Features

* AI-powered email summarization 
* AI based reply generation 
* Gmail integration with OAuth2
* MIME-based email parsing
* Clean layered architecture
* Exception handling & logging

---

## 🚀 Future Enhancements

* Token persistence for OAuth (avoid repeated auth)
* HTML → clean text conversion
* Pagination for Gmail API
* Async processing
* UI integration
* RAG-based email search

---

## ⚠️ Notes

* Uses LLM via API (no custom training)
* Free tier APIs may have usage limits
* OAuth required for Gmail access

---

## 👨‍💻 Author

**Hempreet Singh**

---

## ⭐ Final Thought

This project goes beyond a simple AI demo and demonstrates:

* Real-world API integration
* AI system design
* Backend architecture
* Performance optimization

---

