import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

const appointmentService = {
  book: (data) => api.post(API_ENDPOINTS.APPOINTMENTS.BOOK, data),

  cancel: (id) => api.patch(API_ENDPOINTS.APPOINTMENTS.CANCEL(id)),

  getHistory: () => api.get(API_ENDPOINTS.APPOINTMENTS.HISTORY),

  getById: (id) => api.get(API_ENDPOINTS.APPOINTMENTS.BY_ID(id)),
};

export default appointmentService;
