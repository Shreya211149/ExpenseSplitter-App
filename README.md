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
- **Language:** Java 21
- **Framework:** Spring Boot 4.0.4
- **Security:** Spring Security + JWT (JJWT 0.11.5)
- **ORM:** Spring Data JPA (Hibernate)
- **Validation:** Jakarta Bean Validation
- **API Documentation:** SpringDoc OpenAPI (Swagger UI 2.6.0)
- **Build Tool:** Maven  

### Database
- **MySQL (Aiven Cloud)**

### Frontend
- **HTML, CSS, JavaScript**
- Responsive UI
- Hosted on Netlify

### Deployment(Updated)
- **Backend:** Render
- **Database:** Aiven Managed MySQL
- **Frontend:** Netlify

---

## 📂 Project Structure

<img width="1031" height="531" alt="Screenshot 2026-04-06 101604" src="https://github.com/user-attachments/assets/0aa75669-336d-4669-a96e-df24ee8ba030" />

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
  https://splitkro-app.onrender.com/swagger-ui/index.html

---

## 🌐 Live Application

- **Frontend (Netlify):**  
  https://splitkro-ui.netlify.app/

- **Backend (Render):**  
  https://splitkro-app.onrender.com/

---

## 🔐 Authentication Flow

1. User registers or logs in
2. Backend returns a JWT token
3. Frontend stores the token
4. Token is sent in headers for protected APIs

## 👩‍💻 Author

Shreya Midya
Email: shreyamidya2003@.com


## ⭐ Support
If you like this project:

⭐ Star the repository
🍴 Fork it
🐞 Report issues
🤝 Contribute improvements
