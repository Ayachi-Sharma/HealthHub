import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

const authService = {
  test: () => api.get(API_ENDPOINTS.AUTH.TEST),

  login: (credentials) => api.post(API_ENDPOINTS.AUTH.LOGIN, credentials),

  registerPatient: (data) => api.post(API_ENDPOINTS.AUTH.REGISTER_PATIENT, data),

  registerDoctor: (data) => api.post(API_ENDPOINTS.AUTH.REGISTER_DOCTOR, data),

  refreshToken: (refreshToken) =>
    api.post(
      API_ENDPOINTS.AUTH.REFRESH,
      {},
      { headers: { Authorization: `Bearer ${refreshToken}` } }
    ),
};

export default authService;
