# MediPay API Testing Guide

## Base URL
```
http://localhost:8080
```

## Authentication Endpoints

### 1. Test API
**GET** `/api/auth/test`

**Response:**
```json
{
  "success": true,
  "message": "MediPay API is running!",
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 2. Register Patient
**POST** `/api/auth/register/patient`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "fullName": "John Doe",
  "email": "john.patient@example.com",
  "password": "password123",
  "phone": "9876543210",
  "address": "123 Main St, City",
  "dateOfBirth": "1990-05-15",
  "gender": "Male",
  "bloodGroup": "O+"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Registration successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "email": "john.patient@example.com",
    "role": "ROLE_PATIENT",
    "userId": 1,
    "message": "Registration successful"
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 3. Register Doctor
**POST** `/api/auth/register/doctor`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "fullName": "Dr. Sarah Smith",
  "email": "sarah.doctor@example.com",
  "password": "password123",
  "phone": "9876543211",
  "specialization": "Cardiologist",
  "experience": 10,
  "qualification": "MBBS, MD (Cardiology)",
  "consultationFee": 500.00,
  "bio": "Experienced cardiologist with 10+ years of practice"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Registration successful. Please wait for admin approval.",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "email": "sarah.doctor@example.com",
    "role": "ROLE_DOCTOR",
    "userId": 2,
    "message": "Registration successful. Please wait for admin approval."
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 4. Login
**POST** `/api/auth/login`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "john.patient@example.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "email": "john.patient@example.com",
    "role": "ROLE_PATIENT",
    "userId": 1,
    "message": "Login successful"
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 5. Refresh Token
**POST** `/api/auth/refresh`

**Headers:**
```
Authorization: Bearer <refresh_token>
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Token refreshed successfully",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "email": "john.patient@example.com",
    "role": "ROLE_PATIENT",
    "userId": 1,
    "message": "Token refreshed successfully"
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

---

## Error Responses

### Validation Error (400 Bad Request)
```json
{
  "status": 400,
  "errors": {
    "email": "Email should be valid",
    "password": "Password should be at least 6 characters"
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

### Email Already Exists (400 Bad Request)
```json
{
  "status": 400,
  "message": "Email already exists",
  "timestamp": "2026-06-11T10:30:00"
}
```

### Invalid Credentials (401 Unauthorized)
```json
{
  "status": 401,
  "message": "Invalid email or password",
  "timestamp": "2026-06-11T10:30:00"
}
```

### Resource Not Found (404 Not Found)
```json
{
  "status": 404,
  "message": "User not found",
  "timestamp": "2026-06-11T10:30:00"
}
```

---

## Testing with cURL

### Test API
```bash
curl http://localhost:8080/api/auth/test
```

### Register Patient
```bash
curl -X POST http://localhost:8080/api/auth/register/patient \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john.patient@example.com",
    "password": "password123",
    "phone": "9876543210",
    "address": "123 Main St, City",
    "dateOfBirth": "1990-05-15",
    "gender": "Male",
    "bloodGroup": "O+"
  }'
```

### Register Doctor
```bash
curl -X POST http://localhost:8080/api/auth/register/doctor \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Dr. Sarah Smith",
    "email": "sarah.doctor@example.com",
    "password": "password123",
    "phone": "9876543211",
    "specialization": "Cardiologist",
    "experience": 10,
    "qualification": "MBBS, MD (Cardiology)",
    "consultationFee": 500.00,
    "bio": "Experienced cardiologist with 10+ years of practice"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.patient@example.com",
    "password": "password123"
  }'
```

### Refresh Token
```bash
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Authorization: Bearer YOUR_REFRESH_TOKEN"
```

---

## Protected Endpoints (Coming in Phase 4+)

To access protected endpoints, include the access token in the Authorization header:

```
Authorization: Bearer <access_token>
```

### Example:
```bash
curl http://localhost:8080/api/patient/profile \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## Role-Based Access

| Role | Can Access |
|------|------------|
| ROLE_PATIENT | `/api/patient/**`, `/api/appointments/**`, `/api/payments/**` |
| ROLE_DOCTOR | `/api/doctor/**`, `/api/appointments/**` |
| ROLE_ADMIN | `/api/admin/**` |

---

## JWT Token Structure

### Access Token (24 hours validity)
```
{
  "sub": "john.patient@example.com",
  "role": "ROLE_PATIENT",
  "iat": 1686480000,
  "exp": 1686566400
}
```

### Refresh Token (7 days validity)
```
{
  "sub": "john.patient@example.com",
  "iat": 1686480000,
  "exp": 1687084800
}
```

---

---

## Patient Endpoints (Phase 4) ✅

### 1. Get Patient Profile
**GET** `/api/patient/profile`

**Headers:**
```
Authorization: Bearer <access_token>
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Profile retrieved successfully",
  "data": {
    "id": 1,
    "userId": 1,
    "fullName": "John Doe",
    "email": "john.patient@example.com",
    "phone": "9876543210",
    "address": "123 Main St, City",
    "dateOfBirth": "1990-05-15",
    "gender": "Male",
    "bloodGroup": "O+"
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 2. Update Patient Profile
**PUT** `/api/patient/profile`

**Headers:**
```
Authorization: Bearer <access_token>
Content-Type: application/json
```

**Request Body:** (All fields optional)
```json
{
  "fullName": "John Updated Doe",
  "phone": "9876543211",
  "address": "456 New Street, City",
  "dateOfBirth": "1990-05-15",
  "gender": "Male",
  "bloodGroup": "A+"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Profile updated successfully",
  "data": {
    "id": 1,
    "userId": 1,
    "fullName": "John Updated Doe",
    "email": "john.patient@example.com",
    "phone": "9876543211",
    "address": "456 New Street, City",
    "dateOfBirth": "1990-05-15",
    "gender": "Male",
    "bloodGroup": "A+"
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 3. Get All Approved Doctors
**GET** `/api/patient/doctors`

**Headers:**
```
Authorization: Bearer <access_token>
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Doctors retrieved successfully",
  "data": [
    {
      "id": 1,
      "userId": 2,
      "fullName": "Dr. Sarah Smith",
      "email": "sarah.doctor@example.com",
      "phone": "9876543211",
      "specialization": "Cardiologist",
      "experience": 10,
      "qualification": "MBBS, MD (Cardiology)",
      "consultationFee": 500.00,
      "bio": "Experienced cardiologist with 10+ years of practice",
      "isApproved": true
    }
  ],
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 4. Search Doctors by Specialization
**GET** `/api/patient/doctors/search?specialization=cardio`

**Headers:**
```
Authorization: Bearer <access_token>
```

**Query Parameters:**
- `specialization` (required): Search term (case-insensitive, partial match)

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Search results retrieved successfully",
  "data": [
    {
      "id": 1,
      "userId": 2,
      "fullName": "Dr. Sarah Smith",
      "email": "sarah.doctor@example.com",
      "phone": "9876543211",
      "specialization": "Cardiologist",
      "experience": 10,
      "qualification": "MBBS, MD (Cardiology)",
      "consultationFee": 500.00,
      "bio": "Experienced cardiologist with 10+ years of practice",
      "isApproved": true
    }
  ],
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 5. Get Doctor by ID
**GET** `/api/patient/doctors/{doctorId}`

**Headers:**
```
Authorization: Bearer <access_token>
```

**Path Parameters:**
- `doctorId` (required): Doctor ID

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Doctor details retrieved successfully",
  "data": {
    "id": 1,
    "userId": 2,
    "fullName": "Dr. Sarah Smith",
    "email": "sarah.doctor@example.com",
    "phone": "9876543211",
    "specialization": "Cardiologist",
    "experience": 10,
    "qualification": "MBBS, MD (Cardiology)",
    "consultationFee": 500.00,
    "bio": "Experienced cardiologist with 10+ years of practice",
    "isApproved": true
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

---

### 6. Get Patient Dashboard
**GET** `/api/patient/dashboard`

**Headers:**
```
Authorization: Bearer <access_token>
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Dashboard data retrieved successfully",
  "data": {
    "totalAppointments": 10,
    "upcomingAppointments": 3,
    "completedAppointments": 5,
    "cancelledAppointments": 2,
    "totalPayments": 7
  },
  "timestamp": "2026-06-11T10:30:00"
}
```

---

## Testing Patient Endpoints with cURL

### Get Profile
```bash
curl http://localhost:8080/api/patient/profile \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Update Profile
```bash
curl -X PUT http://localhost:8080/api/patient/profile \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Updated Doe",
    "phone": "9876543211"
  }'
```

### Get All Doctors
```bash
curl http://localhost:8080/api/patient/doctors \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Search Doctors
```bash
curl "http://localhost:8080/api/patient/doctors/search?specialization=cardio" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Get Doctor by ID
```bash
curl http://localhost:8080/api/patient/doctors/1 \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Get Dashboard
```bash
curl http://localhost:8080/api/patient/dashboard \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

---

## Next Phase APIs (Coming Soon)

- Doctor endpoints (Phase 5)
- Appointment booking endpoints (Phase 4-5)
- Payment endpoints (Phase 6)
- Admin endpoints (Phase 7)
