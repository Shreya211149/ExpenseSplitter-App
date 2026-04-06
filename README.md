# 🚀 SplitKro — Expense Splitter Application

SplitKro is a **Splitwise-style expense management application** that helps users manage shared expenses, split bills fairly, and track debts within groups.  
It provides a **secure Spring Boot REST API** backed by MySQL and a **modern frontend** deployed separately.

---

## ✨ Features

- 🔐 JWT-based authentication (stateless & secure)
- 👤 User registration and login
- 👥 Create groups and manage members
- 💸 Add expenses with multiple split strategies:
  - Equal split
  - Exact amount
  - Percentage-based split
- 📊 View all expenses within a group
- 🔄 Calculate net debts — who owes whom and how much
- ⚠️ Global exception handling with consistent JSON responses
- 📘 Interactive API documentation via Swagger UI

---

## 🛠️ Tech Stack

### Backend
- **Language:** Java 17  
- **Framework:** Spring Boot 3.2  
- **Security:** Spring Security + JWT  
- **ORM:** Spring Data JPA (Hibernate)  
- **Validation:** Jakarta Bean Validation  
- **API Documentation:** SpringDoc OpenAPI (Swagger UI)  
- **Build Tool:** Maven  

### Database
- **MySQL 8**

### Frontend
- **HTML, CSS, JavaScript**
- Responsive UI
- Hosted on Netlify

### Deployment
- **Backend:** Railway
- **Database:** Railway Managed MySQL
- **Frontend:** Netlify

---

## 📂 Project Structure
src/main/java/com/example/splitkro
│
├── config → Swagger & application configs
├── controller → REST controllers
├── dto
│ ├── request → API request DTOs
│ └── response → API response DTOs
├── enum → Enums (SplitType)
├── exception → Custom exceptions & handlers
├── model → JPA entities
├── repository → Spring Data JPA repositories
├── security → JWT & Spring Security config
├── service → Business logic
├── transformer → Entity ↔ DTO mapping
└── SplitkroApplication.java

---

## 🔗 API Endpoints

### 👤 Users

| Method | Endpoint | Auth | Description |
|------|--------|------|------------|
| POST | `/api/users/register` | ❌ | Register a new user |
| POST | `/api/users/login` | ❌ | Login and get JWT |
| GET | `/api/users/{id}` | ✅ | Get user by ID |
| GET | `/api/users` | ✅ | Get all users |

---

### 👥 Groups

| Method | Endpoint | Auth | Description |
|------|--------|------|------------|
| POST | `/api/groups` | ✅ | Create a new group |
| GET | `/api/groups/{id}` | ✅ | Get group by ID |
| GET | `/api/groups?userId=` | ✅ | Get groups for a user |

---

### 💸 Expenses

| Method | Endpoint | Auth | Description |
|------|--------|------|------------|
| POST | `/api/expenses` | ✅ | Create expense with split |
| GET | `/api/expenses?groupId=` | ✅ | Get expenses for a group |

---

### 🔄 Debts

| Method | Endpoint | Auth | Description |
|------|--------|------|------------|
| GET | `/api/debts?groupId=` | ✅ | Get net debts for a group |

---

## 📘 Swagger API Documentation

Test and explore the APIs using Swagger UI:

- **Swagger UI:**  
  https://expensesplitter-app-production.up.railway.app/swagger-ui/index.html

---

## 🌐 Live Application

- **Frontend (Netlify):**  
  https://splitkro-ui.netlify.app/

- **Backend (Railway):**  
  https://expensesplitter-app-production.up.railway.app/

---

## 🔐 Authentication Flow

1. User registers or logs in
2. Backend returns a JWT token
3. Frontend stores the token
4. Token is sent in headers for protected APIs

## 👩‍💻 Author

Shreya Midya
Email: shreyamidya2003@.com
⭐ Support

## If you like this project:

⭐ Star the repository
🍴 Fork it
🐞 Report issues
🤝 Contribute improvements
