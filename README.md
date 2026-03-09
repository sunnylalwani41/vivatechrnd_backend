# VivaTech RnD Backend Assignment

A **Spring Boot REST API** that implements **User Management, Role-Based Access Control, and OTP Authentication using JWT**.

The system allows users to register using a mobile number, verify via OTP, and access secured APIs based on their assigned role and permissions.

---

# Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Hibernate / JPA
- PostgreSQL
- Maven
- Fast2SMS API (OTP Service)
- OkHttp Client

---

# Features

## 1️⃣ User Management
- Create a new user
- Check if user exists
- OTP verification for login
- JWT token generation after OTP verification

## 2️⃣ Role Management
- Create role
- Assign access controls to roles
- Fetch role details
- Update role permissions

## 3️⃣ Access Control
- Define access permissions
- Assign multiple permissions to roles
- Restrict APIs using role-based authorization

## 4️⃣ OTP Authentication
- OTP sent to mobile number
- OTP expiry validation
- Secure login using JWT token

---

# Project Architecture

```
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

Additional Security Layer:

```
Security
 ├── JWT Filter
 ├── CustomUserDetailsService
 └── Security Configuration
```

---

# Database Entities

## User

| Field | Description |
|------|-------------|
uuid | Unique user id |
name | User name |
contactNumber | Mobile number |
otp | OTP for verification |
otpExpiryTime | OTP expiration time |
isVerify | OTP verification status |
role | Assigned user role |

---

## Role

| Field | Description |
|------|-------------|
uuid | Role id |
roleName | Role name |
accessControl | List of permissions |

---

## AccessControl

Defines which APIs a role can access.

---

# API Endpoints

## User APIs

### Add User

```
POST /add_user
```

Body

```json
{
  "name": "Krishna",
  "contactNumber": "8890918364",
  "roleId": "role_uuid"
}
```

---

### Send OTP

```
GET /otp_send?contactNumber=8890918364
```

---

### Verify OTP

```
POST /otp_submit?contactNumber=8890918364&otp=123456
```

Response

```json
{
  "name": "Krishna",
  "contactNumber": "8890918364",
  "token": "JWT_TOKEN"
}
```

---

### Check Existing User

```
GET /check_user?contactNumber=8890918364
```

---

## Role APIs

### Add Role

```
POST /add_role
```

---

### Update Access Control

```
PATCH /update_access/{roleId}
```

---

### Get Role By Id

```
GET /get_role/{roleId}
```

---

### Get All Roles

```
GET /get_all_role
```

---

## Access Control APIs

### Add Access Control

```
POST /add_access_control
```

---

### Get All Access Controls

```
GET /get_all_access_control
```

---

# Security Implementation

The project uses **Spring Security with JWT authentication**.

Authentication Flow:

```
User registers
      ↓
OTP sent to mobile
      ↓
User verifies OTP
      ↓
JWT token generated
      ↓
Token used to access secured APIs
```

---

# Running the Project

## 1️⃣ Clone Repository

```
git clone https://github.com/sunnylalwani41/vivatechrnd_backend.git
```

## 2️⃣ Navigate to Project

```
cd vivatechrnd_backend
```

## 3️⃣ Build Project

```
mvn clean install
```

## 4️⃣ Run Application

```
mvn spring-boot:run
```

---

# Environment Variables

Add the following in **application.properties**

```
spring.datasource.url=jdbc:mysql://localhost:3306/database
spring.datasource.username=root
spring.datasource.password=password

secret.key=your_jwt_secret

fast2sms.api.key=your_fast2sms_key
```

---

# API Testing

You can test APIs using:

- Postman
- Curl
- REST Client

A Postman collection is included for testing all endpoints.
<a href="" download>Click Here To Download The Postman Json File</a> 
---

# Error Handling

Custom exceptions implemented:

- `UserException`
- `RoleException`

Global error response format:

```json
{
  "message": "Error description",
  "detail": "Request URI",
  "localDateTime": "Timestamp"
}
```

---

# Future Improvements

- Redis based OTP storage
- Rate limiting for OTP requests
- Swagger API documentation
- Role based endpoint authorization
- Refresh token support

---

# Author

**Sunny Lalwani**

Full Stack Java Developer

Tech Stack

- Java
- Spring Boot
- Hibernate
- PostgreSQL / MySQL
- JavaScript
- REST APIs
