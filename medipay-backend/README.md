# MediPay Backend

Healthcare Appointment & Payment Platform - Spring Boot Backend

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security**
- **Spring Data JPA**
- **MySQL Database**
- **JWT Authentication**
- **Razorpay Payment Gateway**
- **Maven**

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Database Setup

1. Install MySQL and create a database:
```sql
CREATE DATABASE medipay_db;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Razorpay Setup

1. Sign up at [Razorpay](https://razorpay.com/)
2. Get your API keys from the dashboard
3. Update in `application.properties`:
```properties
razorpay.key.id=YOUR_KEY_ID
razorpay.key.secret=YOUR_KEY_SECRET
```

## Installation

1. Clone the repository
2. Navigate to the backend directory:
```bash
cd medipay-backend
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The server will start at `http://localhost:8080`

## Project Structure

```
src/main/java/com/medipay/
├── config/          # Security & Configuration
├── controller/      # REST Controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA Entities
├── repository/     # JPA Repositories
├── service/        # Business Logic
├── exception/      # Exception Handling
└── util/           # Utility Classes
```

## API Endpoints

### Authentication
- POST `/api/auth/register/patient` - Patient Registration
- POST `/api/auth/register/doctor` - Doctor Registration
- POST `/api/auth/login` - Login

### Patient
- GET `/api/patient/profile` - Get Patient Profile
- PUT `/api/patient/profile` - Update Patient Profile
- GET `/api/patient/doctors` - Get All Approved Doctors
- GET `/api/patient/doctors/search?specialization=` - Search Doctors

### Doctor
- GET `/api/doctor/profile` - Get Doctor Profile
- PUT `/api/doctor/profile` - Update Doctor Profile
- GET `/api/doctor/appointments` - Get Doctor Appointments
- POST `/api/doctor/slots` - Create Time Slots
- GET `/api/doctor/earnings` - Get Earnings

### Appointments
- POST `/api/appointments/book` - Book Appointment
- GET `/api/appointments/patient/{patientId}` - Get Patient Appointments
- GET `/api/appointments/doctor/{doctorId}` - Get Doctor Appointments
- PUT `/api/appointments/{id}/approve` - Approve Appointment
- PUT `/api/appointments/{id}/reject` - Reject Appointment
- DELETE `/api/appointments/{id}` - Cancel Appointment

### Payments
- POST `/api/payments/create-order` - Create Payment Order
- POST `/api/payments/verify` - Verify Payment
- GET `/api/payments/patient/{patientId}` - Get Patient Payment History
- GET `/api/payments/doctor/{doctorId}` - Get Doctor Payment History

### Admin
- GET `/api/admin/dashboard` - Admin Dashboard Stats
- GET `/api/admin/doctors` - Get All Doctors
- PUT `/api/admin/doctors/{id}/approve` - Approve Doctor
- PUT `/api/admin/doctors/{id}/block` - Block Doctor
- GET `/api/admin/patients` - Get All Patients
- GET `/api/admin/revenue` - Get Platform Revenue

## Default Roles

The application automatically creates three roles:
- `ROLE_ADMIN`
- `ROLE_DOCTOR`
- `ROLE_PATIENT`

## Development

### Run in Development Mode
```bash
mvn spring-boot:run
```

### Run Tests
```bash
mvn test
```

### Build JAR
```bash
mvn clean package
```

## Deployment

### Environment Variables for Production

Set these environment variables:
```
SPRING_DATASOURCE_URL=jdbc:mysql://your-db-host:3306/medipay_db
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password
JWT_SECRET=your_jwt_secret_key
RAZORPAY_KEY_ID=your_razorpay_key_id
RAZORPAY_KEY_SECRET=your_razorpay_key_secret
```

### Deploy to Render/Railway

1. Push code to GitHub
2. Connect repository to Render/Railway
3. Set environment variables
4. Deploy

## Phase 2 Status: ✅ Complete
- ✅ Project structure created
- ✅ Maven dependencies configured
- ✅ Database configuration
- ✅ All entity classes created (7 entities)
- ✅ All repository interfaces created (7 repositories)
- ✅ Exception handling setup
- ✅ Utility classes created

## Phase 3 Status: ✅ Complete
- ✅ JWT Utility implemented
- ✅ JWT Authentication Filter
- ✅ Spring Security Configuration
- ✅ UserDetailsService implementation
- ✅ CORS Configuration
- ✅ Authentication DTOs (Request/Response)
- ✅ Authentication Service
- ✅ Authentication Controller
- ✅ Data Initializer (Role seeding)
- ✅ API Testing documentation

### API Endpoints Available:

#### Authentication
- POST `/api/auth/register/patient` - Patient Registration
- POST `/api/auth/register/doctor` - Doctor Registration
- POST `/api/auth/login` - Login
- POST `/api/auth/refresh` - Refresh Token
- GET `/api/auth/test` - Test API

#### Patient Module
- GET `/api/patient/profile` - Get Patient Profile
- PUT `/api/patient/profile` - Update Patient Profile
- GET `/api/patient/doctors` - Get All Approved Doctors
- GET `/api/patient/doctors/search` - Search Doctors by Specialization
- GET `/api/patient/doctors/{id}` - Get Doctor Details
- GET `/api/patient/dashboard` - Get Patient Dashboard

## Phase 4 Status: ✅ Complete
- ✅ Patient Service implemented
- ✅ Patient Controller created
- ✅ Patient DTOs (3 response, 1 request)
- ✅ Profile management (get/update)
- ✅ Doctor listing & search
- ✅ Doctor profile view
- ✅ Patient dashboard

### Next Phase: Phase 5 - Doctor Module
- Doctor Service & Controller
- Time Slot Management
- Appointment Management
- Doctor Dashboard
