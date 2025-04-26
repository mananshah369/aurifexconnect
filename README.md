# 🏢 AurifexConnect – ERP System

A Spring Boot-based **Enterprise Resource Planning (ERP)** backend system for managing users, roles, inventory, customer data, and more.  
Built using modern Java 21, SQLite, and powerful tools like Spring Security, JPA, MapStruct, and Swagger.

---

## ✨ Features
~~~~
- 👤 User registration and management
- 🔐 Role-based access control (RBAC) – `ADMIN`, `USER`
- 📦 Inventory & resource modules (expandable)
- ✅ Input validation (`@Email`, `@Size`, `@Pattern`, etc.)
- 🗄️ SQLite database integration
- 📖 Swagger UI for interactive API documentation
- 🧪 Unit testing with JUnit 5 and Mockito

---

## 🛠 Tech Stack
~~~~
| Layer         | Technology                       |
|---------------|----------------------------------|
| Backend       | Java 21, Spring Boot 3.4.4       |
| ORM           | Spring Data JPA                  |
| Security      | Spring Security                  |
| Mapping       | MapStruct                        |
| Database      | SQLite                           |
| Build Tool    | Maven                            |
| Testing       | JUnit 5, Mockito (inline)        |
| Docs          | Swagger (Springdoc OpenAPI)      |
| Code Cleaner  | Lombok                           |

---

## 📁 Project Setup

### 🔧 Prerequisites

- ✅ Java JDK 21
- ✅ Maven 3.8+
- ✅ Git
- ✅ IDE like IntelliJ IDEA or Eclipse

> ℹ️ SQLite JDBC driver is automatically handled by Maven

---

## 🚀 How to Run the Project

### 1. Clone the Repository

```bash
# Clone the repository
git clone https://github.com/your-username/aurifexconnect.git

# Navigate into the project directory
cd aurifexconnect
```

### 2. Build and Run the Project

```bash
# Build the project
mvn clean install

# Run the Spring Boot application
mvn spring-boot:run
```

---

## ✅ Access the Application

- Main URL: http://localhost:8080

- Swagger UI:
http://localhost:8080/swagger-ui.html
or
http://localhost:8080/swagger-ui/index.html

## 🧪 How to Run Test Cases

The project includes unit tests written using **JUnit 5** and **Mockito** to validate business logic, security, and data handling.

### ✅ Run All Test Cases

```bash

git clone https://github.com/your-username/ERP.git

cd ERP
=======
mvn test
```

## 🗂️ Project Structure
```
src
├── main
│   ├── java
│   │   └── com.aurifexconnect.erp
│   │       ├── controller
│   │       ├── model
│   │       ├── dto
│   │       ├── mapper
│   │       ├── repository
│   │       ├── service
│   │       └── security
│   └── resources
│       ├── application.properties
│       └── data.sql (optional)
└── test
└── java
└── com.aurifexconnect.erp
```

