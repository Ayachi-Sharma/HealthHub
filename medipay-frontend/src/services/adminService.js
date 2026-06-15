import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

const adminService = {
  getDashboard: () => api.get(API_ENDPOINTS.ADMIN.DASHBOARD),

  getUsers: () => api.get(API_ENDPOINTS.ADMIN.USERS),

  updateUserStatus: (userId, data) =>
    api.patch(`${API_ENDPOINTS.ADMIN.USERS}/${userId}/status`, data),

  getDoctors: () => api.get(API_ENDPOINTS.ADMIN.DOCTORS),

  approveDoctor: (doctorId, data) =>
    api.patch(`${API_ENDPOINTS.ADMIN.DOCTORS}/${doctorId}/approval`, data),

  getPatients: () => api.get(API_ENDPOINTS.ADMIN.PATIENTS),

  getPatientById: (id) => api.get(`${API_ENDPOINTS.ADMIN.PATIENTS}/${id}`),

  getAppointments: () => api.get(API_ENDPOINTS.ADMIN.APPOINTMENTS),

  getPayments: () => api.get(API_ENDPOINTS.ADMIN.PAYMENTS),

  getStats: () => api.get(API_ENDPOINTS.ADMIN.STATS),
};

export default adminService;
