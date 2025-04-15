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

## JSON Files used in Test Cases
- [Morning Receipt](./src/test/resources/json/morning_receipt.json)
- [Simple Receipt](./src/test/resources/json/simple_receipt.json)
- [Target Receipt](./src/test/resources/json/target_receipt.json)
- [M&M Corner Market](./src/test/resources/json/mm_corner_market_receipt.json)

---

## 🛠️ Prerequisites to  run locally without docker

- Java 18+ installed
- Gradle (or use the Gradle Wrapper)

## 🧰 Setup & Run

### Clone the repo

```bash
git clone https://github.com/jam928/receipt-processor-api
cd receipt-processor-api
gradle clean build
gradle bootRun // to run the app 
gradle tests // to run the automated unit and integration tests

```

