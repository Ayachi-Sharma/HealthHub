import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

const doctorService = {
  getProfile: () => api.get(API_ENDPOINTS.DOCTOR.PROFILE),

  updateProfile: (data) => api.put(API_ENDPOINTS.DOCTOR.PROFILE, data),

  getDashboard: () => api.get(API_ENDPOINTS.DOCTOR.DASHBOARD),

  getSlots: () => api.get(API_ENDPOINTS.DOCTOR.SLOTS),

  createSlot: (data) => api.post(API_ENDPOINTS.DOCTOR.SLOTS, data),

  deleteSlot: (id) => api.delete(`${API_ENDPOINTS.DOCTOR.SLOTS}/${id}`),

  getAppointments: () => api.get(API_ENDPOINTS.DOCTOR.APPOINTMENTS),

  updateAppointmentStatus: (id, status) =>
    api.patch(`${API_ENDPOINTS.DOCTOR.APPOINTMENTS}/${id}/status`, { status }),

  getEarnings: () => api.get(API_ENDPOINTS.DOCTOR.EARNINGS),
};

export default doctorService;
