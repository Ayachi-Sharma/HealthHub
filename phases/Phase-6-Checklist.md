# Phase 6: Payment Integration - Checklist

## ✅ DTOs - Request Objects (2 files)

- [x] AppointmentBookingRequest.java
  - [x] Doctor ID with @NotNull validation
  - [x] Time slot ID with @NotNull validation
  - [x] Appointment date with @Future validation
  - [x] Optional notes field
  - [x] Lombok annotations

- [x] PaymentVerificationRequest.java
  - [x] Razorpay order ID with @NotBlank validation
  - [x] Razorpay payment ID with @NotBlank validation
  - [x] Razorpay signature with @NotBlank validation
  - [x] Appointment ID with @NotNull validation
  - [x] Lombok annotations

## ✅ DTOs - Response Objects (2 files)

- [x] PaymentOrderResponse.java
  - [x] Order ID (internal)
  - [x] Razorpay order ID
  - [x] Amount and currency
  - [x] Razorpay key ID (for frontend)
  - [x] Appointment ID
  - [x] Doctor name and specialization
  - [x] Appointment date and time
  - [x] Builder pattern

- [x] PaymentResponse.java
  - [x] Payment ID
  - [x] Appointment ID
  - [x] Razorpay order ID
  - [x] Razorpay payment ID
  - [x] Amount and currency
  - [x] Payment status
  - [x] Patient and doctor names
  - [x] Specialization
  - [x] Created at and updated at timestamps
  - [x] Builder pattern

## ✅ Configuration (1 file)

- [x] RazorpayConfig.java
  - [x] @Configuration annotation
  - [x] Inject razorpay.key.id from properties
  - [x] Inject razorpay.key.secret from properties
  - [x] @Bean for RazorpayClient
  - [x] Initialize RazorpayClient with credentials
  - [x] Error handling for initialization failure
  - [x] Logging for debugging
  - [x] @Slf4j annotation

## ✅ Service Layer

### PaymentService.java (1 new file)

- [x] PaymentService.java
  
  **Dependencies:**
  - [x] PaymentRepository autowired
  - [x] AppointmentRepository autowired
  - [x] PatientRepository autowired
  - [x] DoctorRepository autowired
  - [x] RazorpayClient autowired
  - [x] @Value for razorpay.key.id
  - [x] @Value for razorpay.key.secret
  - [x] @Slf4j for logging

  **Authentication & Security:**
  - [x] getCurrentUserEmail() method
    - [x] Get authentication from security context
    - [x] Validate authentication
    - [x] Return user email
    - [x] Throw UnauthorizedException if not authenticated

  - [x] getCurrentPatient() method
    - [x] Get current user email
    - [x] Find patient by email
    - [x] Throw ResourceNotFoundException if not found
    - [x] Return patient entity

  **Payment Order Creation:**
  - [x] createPaymentOrder() method
    - [x] Get current patient
    - [x] Find appointment by ID
    - [x] Verify appointment ownership
    - [x] Check appointment status (APPROVED only)
    - [x] Check for existing SUCCESS payment
    - [x] Get doctor and consultation fee
    - [x] Create Razorpay order JSON
    - [x] Convert amount to paise (multiply by 100)
    - [x] Set currency to INR
    - [x] Generate receipt (APPT_{id})
    - [x] Add notes with IDs
    - [x] Call Razorpay API to create order
    - [x] Get razorpay_order_id from response
    - [x] Create payment entity (PENDING status)
    - [x] Save payment to database
    - [x] Log order creation
    - [x] Build PaymentOrderResponse
    - [x] Return order details
    - [x] Handle RazorpayException
    - [x] @Transactional annotation

  **Payment Verification:**
  - [x] verifyPayment() method
    - [x] Get current patient
    - [x] Find appointment by ID
    - [x] Verify appointment ownership
    - [x] Find payment by razorpay_order_id
    - [x] Call verifyRazorpaySignature()
    - [x] If signature invalid, set FAILED status
    - [x] If valid, update payment with payment_id
    - [x] Set status to SUCCESS
    - [x] Save payment
    - [x] Log successful verification
    - [x] Map to PaymentResponse
    - [x] Return payment details
    - [x] @Transactional annotation

  **Payment History:**
  - [x] getMyPayments() method
    - [x] Get current patient
    - [x] Query payments by patient
    - [x] Order by created_at desc
    - [x] Map to PaymentResponse list
    - [x] Return payment list

  - [x] getPaymentById() method
    - [x] Get current patient
    - [x] Find payment by ID
    - [x] Verify ownership
    - [x] Map to PaymentResponse
    - [x] Return payment details

  - [x] getPaymentByAppointment() method
    - [x] Get current patient
    - [x] Find appointment by ID
    - [x] Verify ownership
    - [x] Find payment by appointment
    - [x] Map to PaymentResponse
    - [x] Return payment details

  **Helper Methods:**
  - [x] verifyRazorpaySignature() method
    - [x] Create payload: order_id|payment_id
    - [x] Initialize Mac with HmacSHA256
    - [x] Create SecretKeySpec with razorpay secret
    - [x] Initialize Mac with secret key
    - [x] Compute HMAC hash
    - [x] Convert to hex string
    - [x] Compare with received signature
    - [x] Return boolean result
    - [x] Handle exceptions and log errors

  - [x] mapToPaymentResponse() method
    - [x] Get appointment details
    - [x] Get patient and doctor details
    - [x] Build PaymentResponse with all fields
    - [x] Return PaymentResponse

### PatientService.java (Enhanced)

- [x] PatientService.java enhancements
  
  **New Dependencies:**
  - [x] TimeSlotRepository autowired

  **Appointment Booking:**
  - [x] bookAppointment() method
    - [x] Get current patient
    - [x] Find doctor by ID
    - [x] Check doctor approval status
    - [x] Find time slot by ID
    - [x] Verify slot belongs to doctor
    - [x] Check if slot is available (not booked)
    - [x] Verify date matches slot date
    - [x] Create appointment entity
    - [x] Set patient, doctor, time slot
    - [x] Set appointment date and time
    - [x] Set status to PENDING
    - [x] Set notes if provided
    - [x] Mark time slot as booked
    - [x] Save time slot
    - [x] Save appointment
    - [x] Map to AppointmentResponse
    - [x] Return appointment details
    - [x] @Transactional annotation

  **Appointment Management:**
  - [x] getMyAppointments() method
    - [x] Get current patient
    - [x] Query appointments by patient
    - [x] Order by date desc, time desc
    - [x] Map to AppointmentResponse list
    - [x] Return appointment list

  - [x] getAppointmentById() method
    - [x] Get current patient
    - [x] Find appointment by ID
    - [x] Verify ownership
    - [x] Map to AppointmentResponse
    - [x] Return appointment details

  - [x] cancelAppointment() method
    - [x] Get current patient
    - [x] Find appointment by ID
    - [x] Verify ownership
    - [x] Check if COMPLETED (cannot cancel)
    - [x] Check if already CANCELLED
    - [x] Set status to CANCELLED
    - [x] Get associated time slot
    - [x] Mark slot as not booked
    - [x] Save time slot
    - [x] Save appointment
    - [x] Map to AppointmentResponse
    - [x] Return updated appointment
    - [x] @Transactional annotation

  **Helper Methods:**
  - [x] mapToAppointmentResponse() method
    - [x] Get patient details
    - [x] Get doctor details
    - [x] Build AppointmentResponse
    - [x] Return AppointmentResponse

## ✅ Controller Layer

### PaymentController.java (1 new file)

- [x] PaymentController.java
  - [x] Base mapping (/api/payment)
  - [x] @PreAuthorize with ROLE_PATIENT
  - [x] CORS enabled
  - [x] Autowired PaymentService

  **Endpoints:**
  - [x] POST /order/{appointmentId}
    - [x] @PathVariable appointmentId
    - [x] Call paymentService.createPaymentOrder()
    - [x] Build ApiResponse
    - [x] Return 200 OK

  - [x] POST /verify
    - [x] @Valid validation
    - [x] Call paymentService.verifyPayment()
    - [x] Build ApiResponse
    - [x] Return 200 OK

  - [x] GET /history
    - [x] Call paymentService.getMyPayments()
    - [x] Build ApiResponse with list
    - [x] Return 200 OK

  - [x] GET /{paymentId}
    - [x] @PathVariable paymentId
    - [x] Call paymentService.getPaymentById()
    - [x] Build ApiResponse
    - [x] Return 200 OK

  - [x] GET /appointment/{appointmentId}
    - [x] @PathVariable appointmentId
    - [x] Call paymentService.getPaymentByAppointment()
    - [x] Build ApiResponse
    - [x] Return 200 OK

### PatientController.java (Enhanced)

- [x] PatientController.java enhancements
  
  **New Imports:**
  - [x] AppointmentBookingRequest import

  **New Endpoints:**
  - [x] POST /appointments/book
    - [x] @Valid validation
    - [x] Call patientService.bookAppointment()
    - [x] Build ApiResponse
    - [x] Return 200 OK

  - [x] GET /appointments
    - [x] Call patientService.getMyAppointments()
    - [x] Build ApiResponse with list
    - [x] Return 200 OK

  - [x] GET /appointments/{appointmentId}
    - [x] @PathVariable appointmentId
    - [x] Call patientService.getAppointmentById()
    - [x] Build ApiResponse
    - [x] Return 200 OK

  - [x] PUT /appointments/{appointmentId}/cancel
    - [x] @PathVariable appointmentId
    - [x] Call patientService.cancelAppointment()
    - [x] Build ApiResponse
    - [x] Return 200 OK

## ✅ Configuration Files

- [x] application.properties
  - [x] razorpay.key.id property
  - [x] razorpay.key.secret property
  - [x] Placeholder values with instructions

- [x] pom.xml
  - [x] Razorpay Java SDK dependency (already present)
  - [x] Version 1.4.3
  - [x] org.json dependency (transitive)

## ✅ Integration

- [x] JWT authentication required
- [x] Role-based access control (ROLE_PATIENT)
- [x] Uses entities from Phase 2
- [x] Uses repositories from Phase 2
- [x] Security context integration
- [x] Exception handling working
- [x] Validation working
- [x] Transactional operations
- [x] Razorpay API integration

## ✅ Business Logic Validation

**Payment Order Creation:**
- [x] User must own appointment
- [x] Appointment must be APPROVED
- [x] No existing SUCCESS payment
- [x] Amount from doctor consultation fee
- [x] Currency set to INR
- [x] Amount converted to paise

**Payment Verification:**
- [x] HMAC-SHA256 signature verification
- [x] Payload: order_id|payment_id
- [x] Secret: Razorpay key secret
- [x] Valid signature → SUCCESS status
- [x] Invalid signature → FAILED status

**Appointment Booking:**
- [x] Doctor must be approved
- [x] Time slot must be available
- [x] Slot must belong to doctor
- [x] Date must match slot date
- [x] Slot marked as booked
- [x] Appointment created with PENDING status

**Appointment Cancellation:**
- [x] User must own appointment
- [x] Cannot cancel COMPLETED
- [x] Cannot cancel already CANCELLED
- [x] Time slot released (marked not booked)
- [x] Status updated to CANCELLED

## ✅ Error Handling

- [x] Appointment not found
- [x] Payment not found
- [x] Unauthorized access
- [x] Appointment not approved
- [x] Duplicate payment
- [x] Doctor not approved
- [x] Time slot not available
- [x] Time slot doesn't belong to doctor
- [x] Date mismatch
- [x] Invalid payment signature
- [x] Razorpay API errors
- [x] Cannot cancel completed/cancelled
- [x] Validation errors

## ✅ Code Quality

- [x] Clean architecture maintained
- [x] Separation of concerns
- [x] Dependency injection
- [x] Transactional operations
- [x] Proper HTTP status codes
- [x] RESTful API design
- [x] Stream API usage
- [x] Builder pattern for DTOs
- [x] Comprehensive error handling
- [x] Logging for debugging
- [x] Security best practices
- [x] Cryptographic signature verification

---

## 📊 Phase 6 Statistics

**Files Created**: 8 files
**Files Enhanced**: 2 files (PatientService, PatientController)
**Lines of Code**: ~900 LOC
**API Endpoints**: 9 endpoints (5 payment + 4 appointment)
**Service Methods**: 10 new methods
**DTOs**: 4 DTOs (2 request, 2 response)

---

## 🎯 Phase 6 Complete!

All payment integration features are implemented:
- ✅ Razorpay SDK integration
- ✅ Payment order creation
- ✅ Payment signature verification (HMAC-SHA256)
- ✅ Payment history tracking
- ✅ Appointment booking workflow
- ✅ Appointment management (view/cancel)
- ✅ Time slot validation and booking
- ✅ JWT authentication integrated
- ✅ Role-based access control
- ✅ Ownership verification
- ✅ Complete error handling
- ✅ Business rule validation

**Ready to proceed to Phase 7: Admin Module**

