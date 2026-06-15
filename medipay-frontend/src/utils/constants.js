export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';
export const APP_NAME = import.meta.env.VITE_APP_NAME || 'MediPay';
export const RAZORPAY_KEY_ID = import.meta.env.VITE_RAZORPAY_KEY_ID || '';

export const ROLES = {
  ADMIN: 'ROLE_ADMIN',
  DOCTOR: 'ROLE_DOCTOR',
  PATIENT: 'ROLE_PATIENT',
};

export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: '/auth/login',
    REGISTER_PATIENT: '/auth/register/patient',
    REGISTER_DOCTOR: '/auth/register/doctor',
    REFRESH: '/auth/refresh',
    TEST: '/auth/test',
  },
  PATIENT: {
    PROFILE: '/patient/profile',
    DASHBOARD: '/patient/dashboard',
    DOCTORS: '/patient/doctors',
    DOCTOR_BY_ID: (id) => `/patient/doctors/${id}`,
    SEARCH_DOCTORS: '/patient/doctors/search',
  },
  DOCTOR: {
    PROFILE: '/doctor/profile',
    DASHBOARD: '/doctor/dashboard',
    SLOTS: '/doctor/slots',
    APPOINTMENTS: '/doctor/appointments',
    EARNINGS: '/doctor/earnings',
  },
  ADMIN: {
    DASHBOARD: '/admin/dashboard',
    USERS: '/admin/users',
    DOCTORS: '/admin/doctors',
    PATIENTS: '/admin/patients',
    APPOINTMENTS: '/admin/appointments',
    PAYMENTS: '/admin/payments',
    STATS: '/admin/stats',
  },
  APPOINTMENTS: {
    BOOK: '/appointments/book',
    CANCEL: (id) => `/appointments/${id}/cancel`,
    HISTORY: '/appointments/history',
    BY_ID: (id) => `/appointments/${id}`,
  },
  PAYMENTS: {
    CREATE_ORDER: '/payments/create-order',
    VERIFY: '/payments/verify',
    HISTORY: '/payments/history',
    BY_ID: (id) => `/payments/${id}`,
    BY_APPOINTMENT: (id) => `/payments/appointment/${id}`,
  },
};

export const STORAGE_KEYS = {
  ACCESS_TOKEN: 'medipay_access_token',
  REFRESH_TOKEN: 'medipay_refresh_token',
  USER: 'medipay_user',
};

export const ROUTES = {
  HOME: '/',
  LOGIN: '/login',
  REGISTER_PATIENT: '/register/patient',
  REGISTER_DOCTOR: '/register/doctor',
  PATIENT_DASHBOARD: '/patient/dashboard',
  DOCTOR_DASHBOARD: '/doctor/dashboard',
  ADMIN_DASHBOARD: '/admin/dashboard',
};
