# 📦 Receipt-Processor-Api

> A RESTful service for managing receipts and calculating points.

---

## 🚀 Tech Stack

- Kotlin
- Spring Boot 3
- Gradle 8
- H2 In Memory Database
- MockK for the tests

---

## To run locally with 🐳 Docker

### Run these commands

```bash
docker compose up gradle-app # to run the app
docker compose up gradle-test # to run the tests
```
Swagger Page: http://localhost:8080/swagger-ui/index.html
--- 

## 🛠️ Prerequisites to  run locally without docker

- Java 18+ installed
- Gradle (or use the Gradle Wrapper)

## 🧰 Setup & Run

### Clone the repo

```bash
git clone https://github.com/yourusername/your-repo.git
cd your-repo
gradle clean build
gradle bootRun // to run the app 
gradle tests // to run the automated unit and integration tests

```

