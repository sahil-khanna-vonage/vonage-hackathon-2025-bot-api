# 📊 CXO Insights API – WhatsApp Query Assistant

An intelligent API service enabling CXOs and managers to text natural language questions over **WhatsApp** and receive instant business insights powered by **LLaMA 3**, **PostgreSQL**, and the **Vonage Communication API**.

---

## 🚀 Overview

This project allows CXOs to ask questions like:

> _"How many calls were answered today?"_

The system replies with:

> _"A total of 22 inbound calls were answered today."_

### 🔁 End-to-End Flow

1. ✅ CXO sends a WhatsApp message.
2. ✅ API receives the message.
3. 🤖 API forwards it to **LLaMA 3** (running locally via **Ollama**) for SQL generation.
4. 🧠 API executes the generated SQL query against **PostgreSQL**.
5. 📲 API replies via **Vonage WhatsApp API** with the result.

---

## 🧰 Tech Stack

- **Spring Boot** – RESTful API Backend  
- **Ollama + LLaMA 3** – Local LLM for SQL generation  
- **PostgreSQL** – Metrics database  
- **Vonage Communication API** – WhatsApp messaging  
- **Docker Compose** – Containerized deployment

---

## 🐳 Docker Setup

### 🔧 Start the Service

```bash
# Load environment variables
export $(cat .local.env | xargs)  # For local development
# OR
export $(cat .prod.env | xargs)   # For production

# Build and run containers
docker-compose build
docker-compose up -d


## 🔍 API Documentation

- **Swagger UI:**  
  [http://localhost:9090/api/swagger-ui/index.html](http://localhost:9090/api/swagger-ui/index.html)

- **Health Check:**  
  [http://localhost:9091/actuator/health](http://localhost:9091/actuator/health)

---

## 📦 Environment Variables

To configure the application, copy the example file and fill in the correct values:

```bash
cp example.env .prod.env
```
Then edit .prod.env with your environment-specific settings.

## 🧪 Sample Data Generation

To populate the database with realistic test data:

➡️ Follow the instructions in the GitHub repository:  
[https://github.com/sahil-khanna-vonage/vonage-hackathon-2025-data-generation-script](https://github.com/sahil-khanna-vonage/vonage-hackathon-2025-data-generation-script)

➡️ After cloning the repo, run the provided script to insert sample records into your PostgreSQL database.