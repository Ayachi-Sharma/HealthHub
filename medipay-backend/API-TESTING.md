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


---

## Doctor Module Endpoints

### 1. Get Doctor Profile
**GET** `/api/doctor/profile`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Profile retrieved successfully",
  "data": {
    "doctorId": 1,
    "userId": 2,
    "fullName": "Dr. Sarah Smith",
    "email": "sarah.doctor@example.com",
    "phone": "9876543211",
    "specialization": "Cardiologist",
    "experience": 10,
    "qualification": "MBBS, MD (Cardiology)",
    "consultationFee": 500.00,
    "bio": "Experienced cardiologist with 10+ years of practice",
    "isApproved": true,
    "createdAt": "2026-06-11T10:00:00"
  },
  "timestamp": "2026-06-11T10:35:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/doctor/profile \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

### 2. Update Doctor Profile
**PUT** `/api/doctor/profile`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
Content-Type: application/json
```

**Request Body (all fields optional):**
```json
{
  "fullName": "Dr. Sarah Johnson",
  "phone": "9876543222",
  "specialization": "Senior Cardiologist",
  "experience": 12,
  "consultationFee": 600.00,
  "bio": "Senior cardiologist with 12+ years of practice"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Profile updated successfully",
  "data": {
    "doctorId": 1,
    "userId": 2,
    "fullName": "Dr. Sarah Johnson",
    "email": "sarah.doctor@example.com",
    "phone": "9876543222",
    "specialization": "Senior Cardiologist",
    "experience": 12,
    "qualification": "MBBS, MD (Cardiology)",
    "consultationFee": 600.00,
    "bio": "Senior cardiologist with 12+ years of practice",
    "isApproved": true,
    "createdAt": "2026-06-11T10:00:00"
  },
  "timestamp": "2026-06-11T10:36:00"
}
```

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/doctor/profile \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "specialization": "Senior Cardiologist",
    "experience": 12,
    "consultationFee": 600.00
  }'
```

---

### 3. Create Time Slot
**POST** `/api/doctor/slots`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
Content-Type: application/json
```

**Request Body:**
```json
{
  "date": "2026-06-20",
  "startTime": "09:00:00",
  "endTime": "10:00:00"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Time slot created successfully",
  "data": {
    "slotId": 1,
    "doctorId": 1,
    "date": "2026-06-20",
    "startTime": "09:00:00",
    "endTime": "10:00:00",
    "isBooked": false
  },
  "timestamp": "2026-06-11T10:37:00"
}
```

**Error Response (400 Bad Request - Overlapping Slot):**
```json
{
  "success": false,
  "message": "Time slot overlaps with existing slot",
  "timestamp": "2026-06-11T10:37:00"
}
```

**Error Response (400 Bad Request - Not Approved):**
```json
{
  "success": false,
  "message": "Your profile is not approved yet. Please wait for admin approval.",
  "timestamp": "2026-06-11T10:37:00"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/doctor/slots \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2026-06-20",
    "startTime": "09:00:00",
    "endTime": "10:00:00"
  }'
```

---

### 4. Get All Time Slots
**GET** `/api/doctor/slots`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Time slots retrieved successfully",
  "data": [
    {
      "slotId": 1,
      "doctorId": 1,
      "date": "2026-06-20",
      "startTime": "09:00:00",
      "endTime": "10:00:00",
      "isBooked": false
    },
    {
      "slotId": 2,
      "doctorId": 1,
      "date": "2026-06-20",
      "startTime": "10:00:00",
      "endTime": "11:00:00",
      "isBooked": true
    }
  ],
  "timestamp": "2026-06-11T10:38:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/doctor/slots \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

### 5. Get Available Slots by Date
**GET** `/api/doctor/slots/available?date={date}`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Query Parameters:**
- `date` (required): Date in format YYYY-MM-DD

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Available slots retrieved successfully",
  "data": [
    {
      "slotId": 1,
      "doctorId": 1,
      "date": "2026-06-20",
      "startTime": "09:00:00",
      "endTime": "10:00:00",
      "isBooked": false
    }
  ],
  "timestamp": "2026-06-11T10:39:00"
}
```

**cURL:**
```bash
curl -X GET "http://localhost:8080/api/doctor/slots/available?date=2026-06-20" \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

### 6. Delete Time Slot
**DELETE** `/api/doctor/slots/{slotId}`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Path Parameters:**
- `slotId`: ID of the time slot to delete

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Time slot deleted successfully",
  "data": null,
  "timestamp": "2026-06-11T10:40:00"
}
```

**Error Response (400 Bad Request - Booked Slot):**
```json
{
  "success": false,
  "message": "Cannot delete a booked time slot",
  "timestamp": "2026-06-11T10:40:00"
}
```

**cURL:**
```bash
curl -X DELETE http://localhost:8080/api/doctor/slots/1 \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

### 7. Get All Appointments
**GET** `/api/doctor/appointments`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Appointments retrieved successfully",
  "data": [
    {
      "appointmentId": 1,
      "patientId": 1,
      "patientName": "John Doe",
      "patientEmail": "john.patient@example.com",
      "patientPhone": "9876543210",
      "doctorId": 1,
      "doctorName": "Dr. Sarah Smith",
      "specialization": "Cardiologist",
      "appointmentDate": "2026-06-20",
      "appointmentTime": "09:00:00",
      "status": "PENDING",
      "notes": null,
      "createdAt": "2026-06-11T10:00:00"
    }
  ],
  "timestamp": "2026-06-11T10:41:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/doctor/appointments \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

### 8. Get Today's Appointments
**GET** `/api/doctor/appointments/today`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Today's appointments retrieved successfully",
  "data": [
    {
      "appointmentId": 2,
      "patientId": 2,
      "patientName": "Jane Smith",
      "patientEmail": "jane@example.com",
      "patientPhone": "9876543211",
      "doctorId": 1,
      "doctorName": "Dr. Sarah Smith",
      "specialization": "Cardiologist",
      "appointmentDate": "2026-06-11",
      "appointmentTime": "14:00:00",
      "status": "APPROVED",
      "notes": null,
      "createdAt": "2026-06-10T08:00:00"
    }
  ],
  "timestamp": "2026-06-11T10:42:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/doctor/appointments/today \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

### 9. Get Upcoming Appointments
**GET** `/api/doctor/appointments/upcoming`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Upcoming appointments retrieved successfully",
  "data": [
    {
      "appointmentId": 3,
      "patientId": 1,
      "patientName": "John Doe",
      "patientEmail": "john.patient@example.com",
      "patientPhone": "9876543210",
      "doctorId": 1,
      "doctorName": "Dr. Sarah Smith",
      "specialization": "Cardiologist",
      "appointmentDate": "2026-06-20",
      "appointmentTime": "09:00:00",
      "status": "APPROVED",
      "notes": "Regular checkup",
      "createdAt": "2026-06-11T10:00:00"
    }
  ],
  "timestamp": "2026-06-11T10:43:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/doctor/appointments/upcoming \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

### 10. Get Appointment by ID
**GET** `/api/doctor/appointments/{appointmentId}`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Path Parameters:**
- `appointmentId`: ID of the appointment

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Appointment retrieved successfully",
  "data": {
    "appointmentId": 1,
    "patientId": 1,
    "patientName": "John Doe",
    "patientEmail": "john.patient@example.com",
    "patientPhone": "9876543210",
    "doctorId": 1,
    "doctorName": "Dr. Sarah Smith",
    "specialization": "Cardiologist",
    "appointmentDate": "2026-06-20",
    "appointmentTime": "09:00:00",
    "status": "PENDING",
    "notes": null,
    "createdAt": "2026-06-11T10:00:00"
  },
  "timestamp": "2026-06-11T10:44:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/doctor/appointments/1 \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

### 11. Update Appointment Status
**PUT** `/api/doctor/appointments/{appointmentId}/status`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
Content-Type: application/json
```

**Path Parameters:**
- `appointmentId`: ID of the appointment

**Request Body:**
```json
{
  "status": "APPROVED",
  "notes": "Appointment confirmed for regular checkup"
}
```

**Possible Status Values:**
- `PENDING`
- `APPROVED`
- `COMPLETED`
- `CANCELLED`
- `REJECTED`

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Appointment status updated successfully",
  "data": {
    "appointmentId": 1,
    "patientId": 1,
    "patientName": "John Doe",
    "patientEmail": "john.patient@example.com",
    "patientPhone": "9876543210",
    "doctorId": 1,
    "doctorName": "Dr. Sarah Smith",
    "specialization": "Cardiologist",
    "appointmentDate": "2026-06-20",
    "appointmentTime": "09:00:00",
    "status": "APPROVED",
    "notes": "Appointment confirmed for regular checkup",
    "createdAt": "2026-06-11T10:00:00"
  },
  "timestamp": "2026-06-11T10:45:00"
}
```

**Error Response (400 Bad Request - Invalid Transition):**
```json
{
  "success": false,
  "message": "Cannot update status of completed appointment",
  "timestamp": "2026-06-11T10:45:00"
}
```

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/doctor/appointments/1/status \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "APPROVED",
    "notes": "Appointment confirmed for regular checkup"
  }'
```

---

### 12. Get Doctor Dashboard
**GET** `/api/doctor/dashboard`

**Headers:**
```
Authorization: Bearer {doctorAccessToken}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Dashboard data retrieved successfully",
  "data": {
    "totalAppointments": 25,
    "todayAppointments": 3,
    "upcomingAppointments": 8,
    "completedAppointments": 15,
    "pendingAppointments": 5,
    "totalEarnings": 7500.00
  },
  "timestamp": "2026-06-11T10:46:00"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/doctor/dashboard \
  -H "Authorization: Bearer YOUR_DOCTOR_ACCESS_TOKEN"
```

---

## Summary of Endpoints

### Authentication (5 endpoints)
- GET `/api/auth/test` - Health check
- POST `/api/auth/register/patient` - Register patient
- POST `/api/auth/register/doctor` - Register doctor
- POST `/api/auth/login` - User login
- POST `/api/auth/refresh` - Refresh token

### Patient Module (6 endpoints)
- GET `/api/patient/profile` - Get profile
- PUT `/api/patient/profile` - Update profile
- GET `/api/patient/doctors` - List doctors
- GET `/api/patient/doctors/search` - Search doctors
- GET `/api/patient/doctors/{id}` - Get doctor details
- GET `/api/patient/dashboard` - Get dashboard

### Doctor Module (12 endpoints)
- GET `/api/doctor/profile` - Get profile
- PUT `/api/doctor/profile` - Update profile
- POST `/api/doctor/slots` - Create time slot
- GET `/api/doctor/slots` - Get all slots
- GET `/api/doctor/slots/available` - Get available slots
- DELETE `/api/doctor/slots/{id}` - Delete slot
- GET `/api/doctor/appointments` - Get all appointments
- GET `/api/doctor/appointments/today` - Today's appointments
- GET `/api/doctor/appointments/upcoming` - Upcoming appointments
- GET `/api/doctor/appointments/{id}` - Get appointment
- PUT `/api/doctor/appointments/{id}/status` - Update status
- GET `/api/doctor/dashboard` - Get dashboard

**Total Endpoints**: 23 endpoints

---

## Testing Workflow Example

### 1. Register & Login as Doctor
```bash
# Register
curl -X POST http://localhost:8080/api/auth/register/doctor \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Dr. Sarah Smith",
    "email": "sarah.doctor@example.com",
    "password": "password123",
    "phone": "9876543211",
    "specialization": "Cardiologist",
    "experience": 10,
    "qualification": "MBBS, MD",
    "consultationFee": 500
  }'

# Save the accessToken from response
# Note: Doctor needs admin approval before creating slots
```

### 2. View Doctor Profile
```bash
curl -X GET http://localhost:8080/api/doctor/profile \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### 3. Create Time Slots (after approval)
```bash
curl -X POST http://localhost:8080/api/doctor/slots \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "date": "2026-06-20",
    "startTime": "09:00:00",
    "endTime": "10:00:00"
  }'
```

### 4. View Dashboard
```bash
curl -X GET http://localhost:8080/api/doctor/dashboard \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

---

## Phase 5 Complete! ✅

All doctor module endpoints are ready for testing. The system now supports:
- ✅ Doctor profile management
- ✅ Time slot creation & management
- ✅ Appointment viewing & status updates
- ✅ Dashboard with statistics and earnings
