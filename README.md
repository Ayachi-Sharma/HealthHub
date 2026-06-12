<<<<<<< HEAD
# 🏥 MediPay - Healthcare Appointment & Payment Platform

[![Phase](https://img.shields.io/badge/Phase-4%20Complete-success)](https://github.com/yourusername/medipay)
[![Progress](https://img.shields.io/badge/Progress-29%25-blue)](https://github.com/yourusername/medipay)
[![Backend](https://img.shields.io/badge/Backend-Spring%20Boot%203.2.0-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

A production-level full-stack healthcare platform enabling patients to book doctor appointments and make secure online payments.

## 📋 Table of Contents
- [About](#about)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Status](#project-status)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Development Roadmap](#development-roadmap)
- [Contributing](#contributing)

## 🎯 About

MediPay is a comprehensive healthcare management system with three user roles:
- **Patients**: Book appointments, make payments, view history
- **Doctors**: Manage availability, handle appointments, view earnings
- **Admins**: Platform management, analytics, user approval

## ✨ Features

### ✅ Implemented Features

#### Authentication & Security (Phase 3)
- JWT-based authentication (24h access, 7d refresh tokens)
- Role-based authorization (Patient, Doctor, Admin)
- BCrypt password encryption
- Token refresh mechanism
- CORS configuration

#### Patient Module (Phase 4)
- Patient registration and login
- Profile management (view/update)
- Browse all approved doctors
- Search doctors by specialization
- View doctor profiles with fees
- Patient dashboard with statistics

### 🔄 In Development
- Doctor Module (Phase 5)
- Appointment Booking (Phase 5-6)
- Payment Integration with Razorpay (Phase 6)
- Admin Dashboard (Phase 7)
- React Frontend (Phase 8-13)

## 🚀 Tech Stack

### Backend (Current)
- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA
- **Build Tool**: Maven
- **Java Version**: 17

### Frontend (Upcoming - Phase 8)
- React.js
- React Router
- Context API
- Axios
- Tailwind CSS

### Payment Gateway (Phase 6)
- Razorpay SDK

### Deployment (Phase 14)
- Backend: Render / Railway
- Frontend: Vercel
- Database: MySQL (Production)

## 📊 Project Status

**Current Phase**: Phase 4 Complete ✅  
**Overall Progress**: 29% (4/14 phases)

| Phase | Status | Description |
|-------|--------|-------------|
| Phase 1 | ✅ | Requirements & Design |
| Phase 2 | ✅ | Backend Core Setup |
| Phase 3 | ✅ | Authentication & Security |
| Phase 4 | ✅ | Patient Module |
| Phase 5 | 🔄 | Doctor Module (Next) |
| Phase 6 | ⏳ | Payment Integration |
| Phase 7 | ⏳ | Admin Module |
| Phase 8-13 | ⏳ | Frontend Development |
| Phase 14 | ⏳ | Testing & Deployment |

### Statistics
- **Backend Files**: 43 Java files
- **Lines of Code**: ~3,850
- **API Endpoints**: 11 endpoints
- **Database Entities**: 7 entities
- **Services**: 3 services
- **Controllers**: 2 controllers

## 🛠️ Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/medipay.git
   cd medipay
   ```

2. **Set up MySQL database**
   ```sql
   CREATE DATABASE medipay_db;
   ```

3. **Configure application properties**
   
   Edit `medipay-backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   
   # JWT Secret (generate a secure key)
   jwt.secret=YOUR_JWT_SECRET_KEY
   
   # Razorpay (for Phase 6)
   razorpay.key.id=YOUR_RAZORPAY_KEY_ID
   razorpay.key.secret=YOUR_RAZORPAY_KEY_SECRET
   ```

4. **Build and run the backend**
   ```bash
   cd medipay-backend
   mvn clean install
   mvn spring-boot:run
   ```

5. **Test the API**
   ```bash
   curl http://localhost:8080/api/auth/test
   ```

   Expected response:
   ```json
   {
     "success": true,
     "message": "MediPay API is running!",
     "timestamp": "2026-06-11T10:30:00"
   }
   ```

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Authentication Endpoints

#### Register Patient
```bash
POST /api/auth/register/patient
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "9876543210",
  "gender": "Male",
  "bloodGroup": "O+"
}
```

#### Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

#### Register Doctor
```bash
POST /api/auth/register/doctor
Content-Type: application/json

{
  "fullName": "Dr. Sarah Smith",
  "email": "sarah@example.com",
  "password": "password123",
  "phone": "9876543211",
  "specialization": "Cardiologist",
  "experience": 10,
  "qualification": "MBBS, MD",
  "consultationFee": 500.00
}
```

### Patient Endpoints (Requires JWT Token)

```bash
# Get patient profile
GET /api/patient/profile
Authorization: Bearer <token>

# Update profile
PUT /api/patient/profile
Authorization: Bearer <token>

# Get all doctors
GET /api/patient/doctors
Authorization: Bearer <token>

# Search doctors
GET /api/patient/doctors/search?specialization=cardio
Authorization: Bearer <token>

# Get doctor details
GET /api/patient/doctors/{id}
Authorization: Bearer <token>

# Get patient dashboard
GET /api/patient/dashboard
Authorization: Bearer <token>
```

For complete API documentation, see [API-TESTING.md](medipay-backend/API-TESTING.md).

## 📁 Project Structure

```
medipay/
├── docs/                          # Design documents
│   ├── Project Architecture
│   ├── Database Design
│   ├── Database Schema
│   └── ...
│
├── phases/                        # Phase tracking
│   ├── Phase-1-Summary.md
│   ├── Phase-2-Summary.md
│   ├── Phase-3-Summary.md
│   ├── Phase-4-Summary.md
│   └── PROGRESS-TRACKER.md
│
├── medipay-backend/              # Spring Boot backend
│   ├── src/
│   │   └── main/
│   │       ├── java/com/medipay/
│   │       │   ├── config/       # Security & configuration
│   │       │   ├── controller/   # REST controllers
│   │       │   ├── dto/          # Data transfer objects
│   │       │   ├── entity/       # JPA entities
│   │       │   ├── repository/   # Data access layer
│   │       │   ├── service/      # Business logic
│   │       │   ├── exception/    # Exception handling
│   │       │   └── util/         # Utilities
│   │       └── resources/
│   │           └── application.properties
│   ├── pom.xml
│   └── README.md
│
├── medipay-frontend/             # React frontend (Phase 8)
│
├── .gitignore
├── README.md
└── LICENSE
```

## 🗺️ Development Roadmap

### Completed Phases ✅

- **Phase 1**: Requirements Analysis & Design
  - System architecture
  - Database design (7 entities)
  - Complete SQL schema
  - Folder structures

- **Phase 2**: Backend Core Setup
  - 7 JPA entities
  - 7 Spring Data repositories
  - Exception handling
  - Application configuration

- **Phase 3**: Authentication & Security
  - JWT implementation
  - Spring Security configuration
  - User registration & login
  - Role-based authorization

- **Phase 4**: Patient Module
  - Profile management
  - Doctor listing & search
  - Doctor profile viewing
  - Patient dashboard

### Upcoming Phases 🔄

- **Phase 5**: Doctor Module
  - Time slot management
  - Appointment handling
  - Doctor dashboard

- **Phase 6**: Payment Integration
  - Razorpay integration
  - Order creation & verification
  - Payment history

- **Phase 7**: Admin Module
  - User management
  - Doctor approval
  - Platform analytics

- **Phase 8-13**: Frontend Development
  - React setup
  - Authentication UI
  - Patient/Doctor/Admin interfaces
  - Payment UI

- **Phase 14**: Testing & Deployment
  - Backend deployment (Render/Railway)
  - Frontend deployment (Vercel)
  - Integration testing

## 🔐 Security Features

- JWT token authentication
- BCrypt password encryption
- Role-based access control
- Protected API endpoints
- CORS configuration
- Input validation
- SQL injection prevention

## 📄 Database Schema

### Main Entities
1. **Role** - User roles (Admin, Doctor, Patient)
2. **User** - Authentication & accounts
3. **Patient** - Patient profiles
4. **Doctor** - Doctor profiles
5. **TimeSlot** - Doctor availability
6. **Appointment** - Booking management
7. **Payment** - Payment processing

For detailed schema, see [Database Schema](docs/Database%20Schema).

## 🤝 Contributing

This is a structured learning project following a 14-phase development plan. Each phase builds upon the previous one.

To contribute:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧 Contact

For questions or collaboration:
- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Razorpay for payment gateway integration
- Open source community

---

**Built with ❤️ as a comprehensive full-stack healthcare platform**

**Current Status**: Phase 4 Complete | Patient Module Working ✅
=======
# HealthHub
>>>>>>> aa223e1ff8638575e7ecd6a6030b4ab7f6e8acd8
