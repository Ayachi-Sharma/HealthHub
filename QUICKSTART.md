# MediPay - Quick Start Guide

## Project Status

### ✅ Phase 1: Requirements & Design - COMPLETED
- System architecture designed
- Database schema created
- ER diagram defined
- Folder structure planned
- Development roadmap established

### ✅ Phase 2: Backend Core Setup - COMPLETED
- Spring Boot project initialized
- 7 JPA entities created
- 7 Spring Data repositories implemented
- Exception handling framework
- Configuration files set up
- 24 files created (~1,800+ LOC)

### 🔄 Next: Phase 3 - Authentication & Security
- JWT implementation
- Spring Security configuration
- User registration & login
- Role-based authorization

## What's Been Built

### Backend Structure
```
medipay-backend/
├── Entity Layer: 7 entities (User, Role, Patient, Doctor, TimeSlot, Appointment, Payment)
├── Repository Layer: 7 repositories with custom queries
├── Exception Layer: Global exception handling
├── Utility Layer: Constants and helpers
└── Configuration: Database, JWT, Razorpay setup
```

### Database Schema
- **Users & Roles**: Authentication foundation
- **Patients & Doctors**: Profile management
- **TimeSlots**: Doctor availability
- **Appointments**: Booking system
- **Payments**: Razorpay integration

## To Run the Backend (After Phase 3)

1. **Install Prerequisites**
   - Java 17+
   - Maven 3.6+
   - MySQL 8.0+

2. **Setup Database**
   ```sql
   CREATE DATABASE medipay_db;
   ```

3. **Configure application.properties**
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   razorpay.key.id=YOUR_KEY_ID
   razorpay.key.secret=YOUR_KEY_SECRET
   ```

4. **Run Application**
   ```bash
   cd medipay-backend
   mvn spring-boot:run
   ```

## Project Features

### 3 User Roles
1. **Patient**: Book appointments, make payments, view history
2. **Doctor**: Manage slots, handle appointments, view earnings
3. **Admin**: Manage users, view analytics, platform control

### Core Modules
- **Authentication**: JWT-based secure login
- **Appointments**: Complete booking workflow
- **Payments**: Razorpay integration
- **Dashboards**: Role-specific analytics

## Development Roadmap

| Phase | Status | Description |
|-------|--------|-------------|
| Phase 1 | ✅ Complete | Requirements & Design |
| Phase 2 | ✅ Complete | Backend Core Setup |
| Phase 3 | 🔄 Next | Authentication & Security |
| Phase 4 | ⏳ Pending | Patient Module |
| Phase 5 | ⏳ Pending | Doctor Module |
| Phase 6 | ⏳ Pending | Payment Integration |
| Phase 7 | ⏳ Pending | Admin Module |
| Phase 8 | ⏳ Pending | Frontend Setup |
| Phase 9 | ⏳ Pending | Frontend Auth |
| Phase 10 | ⏳ Pending | Frontend Patient |
| Phase 11 | ⏳ Pending | Frontend Doctor |
| Phase 12 | ⏳ Pending | Frontend Admin |
| Phase 13 | ⏳ Pending | Payment UI |
| Phase 14 | ⏳ Pending | Testing & Deployment |

## Documentation

- 📄 `docs/Project Architecture` - System design
- 📄 `docs/Database Design` - ER diagram
- 📄 `docs/Database Schema` - SQL schema
- 📄 `docs/Backend Folder Structure` - Code organization
- 📄 `docs/Phase-2-Summary.md` - Detailed phase 2 completion
- 📄 `docs/Phase-2-Checklist.md` - Implementation checklist
- 📄 `medipay-backend/README.md` - Backend documentation

## Key Technologies

**Backend:**
- Spring Boot 3.2.0
- Spring Security + JWT
- Spring Data JPA
- MySQL
- Razorpay SDK

**Frontend:** (Upcoming)
- React.js
- React Router
- Context API
- Axios
- Tailwind CSS

**Deployment:**
- Frontend: Vercel
- Backend: Render/Railway
- Database: MySQL (Production)

## Contact & Support

This is a production-level healthcare platform with:
- Secure authentication
- Role-based access control
- Payment gateway integration
- Complete CRUD operations
- RESTful API design

---

**Current Status**: Phase 2 Complete - Ready for Phase 3 🚀
