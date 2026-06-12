# Phase 2: Backend Core Setup - COMPLETED ✅

## Overview
Phase 2 focused on setting up the Spring Boot backend infrastructure with database entities, repositories, and core configurations.

## What Was Built

### 1. Project Configuration
- **pom.xml**: Maven dependencies including Spring Boot, Spring Security, Spring Data JPA, MySQL, JWT, Razorpay
- **application.properties**: Database, JWT, and Razorpay configurations
- **MediPayApplication.java**: Main Spring Boot application class with JPA Auditing

### 2. Entity Classes (7 Entities)

#### Role.java
- Manages user roles (Admin, Doctor, Patient)
- Fields: id, name, createdAt

#### User.java
- Core authentication entity
- Fields: id, email, password, role, isActive, createdAt, updatedAt
- Relationship: ManyToOne with Role

#### Patient.java
- Patient profile information
- Fields: id, user, fullName, phone, address, dateOfBirth, gender, bloodGroup
- Relationship: OneToOne with User

#### Doctor.java
- Doctor profile information
- Fields: id, user, fullName, phone, specialization, experience, qualification, consultationFee, bio, isApproved
- Relationship: OneToOne with User

#### TimeSlot.java
- Doctor availability management
- Fields: id, doctor, slotDate, startTime, endTime, isAvailable
- Relationship: ManyToOne with Doctor
- Unique constraint on (doctor_id, slot_date, start_time)

#### Appointment.java
- Appointment booking and management
- Fields: id, patient, doctor, appointmentDate, appointmentTime, status, reason, isPaid
- Status enum: PENDING, APPROVED, REJECTED, COMPLETED, CANCELLED
- Relationships: ManyToOne with Patient and Doctor

#### Payment.java
- Payment processing and history
- Fields: id, appointment, patient, doctor, amount, razorpayOrderId, razorpayPaymentId, razorpaySignature, status, paymentDate
- Status enum: PENDING, SUCCESS, FAILED, REFUNDED
- Relationships: OneToOne with Appointment, ManyToOne with Patient and Doctor

### 3. Repository Layer (7 Repositories)

#### RoleRepository
- Find by name
- Check existence by name

#### UserRepository
- Find by email
- Check existence by email
- Find active users by ID

#### PatientRepository
- Find by user ID
- Find by user email
- Check existence by user ID

#### DoctorRepository
- Find by user ID
- Find by user email
- Find approved/unapproved doctors
- Search by specialization (case-insensitive)

#### TimeSlotRepository
- Find by doctor and date
- Find available slots by doctor
- Find available slots in date range
- Find existing slots (prevent duplicates)

#### AppointmentRepository
- Find by patient/doctor ID
- Find by patient/doctor ID with status
- Find upcoming appointments
- Count completed appointments by doctor
- Count appointments by status

#### PaymentRepository
- Find by appointment/patient/doctor ID
- Find by Razorpay order ID
- Find by status
- Calculate total earnings by doctor
- Calculate total platform revenue
- Count successful payments

### 4. Exception Handling

#### Custom Exceptions
- **ResourceNotFoundException**: For missing resources
- **BadRequestException**: For invalid requests
- **UnauthorizedException**: For authentication failures

#### GlobalExceptionHandler
- Centralized exception handling
- Validation error handling
- Custom error response format
- HTTP status code mapping

### 5. Utility Classes

#### Constants.java
- Role constants
- JWT constants
- API endpoint constants
- Common messages

### 6. Documentation
- Comprehensive README.md with setup instructions
- API endpoint documentation
- Deployment guidelines
- .gitignore for version control

## Database Features

### JPA Auditing
- Automatic timestamps for createdAt and updatedAt fields
- Using @CreatedDate and @LastModifiedDate annotations

### Relationships
- Proper foreign key constraints
- Lazy/Eager loading optimization
- Cascade operations for data integrity

### Unique Constraints
- Email uniqueness in Users
- User ID uniqueness in Patient/Doctor
- Time slot uniqueness (doctor, date, time)
- Appointment uniqueness per payment

## Technical Highlights

1. **Clean Architecture**: Separation of concerns with entity, repository, and exception layers
2. **Lombok Integration**: Reduced boilerplate code with @Data, @NoArgsConstructor, @AllArgsConstructor
3. **Type Safety**: Using enums for status fields (AppointmentStatus, PaymentStatus)
4. **Query Optimization**: Custom JPQL queries for complex operations
5. **Validation Ready**: Structure prepared for Jakarta validation annotations
6. **Security Ready**: Password encryption and JWT setup prepared

## File Structure Created

```
medipay-backend/
├── pom.xml
├── .gitignore
├── README.md
└── src/
    └── main/
        ├── java/com/medipay/
        │   ├── MediPayApplication.java
        │   ├── entity/
        │   │   ├── Role.java
        │   │   ├── User.java
        │   │   ├── Patient.java
        │   │   ├── Doctor.java
        │   │   ├── TimeSlot.java
        │   │   ├── Appointment.java
        │   │   └── Payment.java
        │   ├── repository/
        │   │   ├── RoleRepository.java
        │   │   ├── UserRepository.java
        │   │   ├── PatientRepository.java
        │   │   ├── DoctorRepository.java
        │   │   ├── TimeSlotRepository.java
        │   │   ├── AppointmentRepository.java
        │   │   └── PaymentRepository.java
        │   ├── exception/
        │   │   ├── ResourceNotFoundException.java
        │   │   ├── BadRequestException.java
        │   │   ├── UnauthorizedException.java
        │   │   └── GlobalExceptionHandler.java
        │   └── util/
        │       └── Constants.java
        └── resources/
            └── application.properties
```

## Ready for Phase 3

The backend core is now ready for:
- JWT Service implementation
- Spring Security configuration
- Authentication controllers and services
- User registration and login functionality
- Role-based authorization

## Next Steps: Phase 3 - Authentication & Security

1. Implement JWT utility class for token generation/validation
2. Create JWT authentication filter
3. Configure Spring Security with role-based access
4. Implement UserDetailsService
5. Create authentication DTOs (request/response)
6. Build authentication service layer
7. Create authentication controller with register/login endpoints
8. Add refresh token functionality
9. Test authentication flow

---

**Phase 2 Status**: ✅ **COMPLETED**

All 24 files created successfully!
