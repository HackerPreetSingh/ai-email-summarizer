# 🚀 AI Email Summarizer

A Spring Boot-based REST API that uses a Large Language Model (LLM) to summarize email content into concise, structured bullet points.

---

## 🧠 Overview

This project demonstrates how to integrate Generative AI into a backend system. It processes raw email text, sends it to an LLM (Gemini API), and returns a clean, structured summary.

The focus is on:

* LLM API integration
* Prompt engineering
* Backend architecture
* Real-world AI use case

---

## ⚙️ Tech Stack

* **Java (Spring Boot)**
* **REST APIs**
* **Google Gemini API (LLM)**
* **Postman (Testing)**

---

## 🔄 How It Works

1. User sends email content via API
2. Backend constructs a structured prompt
3. Request sent to Gemini API
4. LLM generates summary
5. Backend parses and returns clean response

---

## 📌 API Endpoint

### POST `/api/summarize`

#### Request:

```json
{
  "emailText": "Hi team, please complete the report by Friday..."
}
```

#### Response:

```json
{
  "summary": [
    "Report deadline is Friday",
    "Team coordination required",
    "Pending approvals needed"
  ]
}
```

---

## 🧪 Features

* Email summarization using LLM
* Structured bullet-point output
* Prompt customization
* Error handling and validation

---

## 🚀 Future Enhancements

* Gmail API integration (auto-fetch emails)
* Reply generation feature
* RAG-based email search
* Database storage for summaries

---

## ⚠️ Note

This project uses a pre-trained LLM via API and does not involve training custom models.

---

## 👨‍💻 Author

* Hempreet Singh
