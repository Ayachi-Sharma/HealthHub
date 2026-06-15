import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

const patientService = {
  getProfile: () => api.get(API_ENDPOINTS.PATIENT.PROFILE),

  updateProfile: (data) => api.put(API_ENDPOINTS.PATIENT.PROFILE, data),

  getDashboard: () => api.get(API_ENDPOINTS.PATIENT.DASHBOARD),

  getDoctors: () => api.get(API_ENDPOINTS.PATIENT.DOCTORS),

  getDoctorById: (id) => api.get(API_ENDPOINTS.PATIENT.DOCTOR_BY_ID(id)),

  searchDoctors: (specialization) =>
    api.get(API_ENDPOINTS.PATIENT.SEARCH_DOCTORS, {
      params: { specialization },
    }),
};

export default patientService;
