import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

const paymentService = {
  createOrder: (appointmentId) =>
    api.post(API_ENDPOINTS.PAYMENTS.CREATE_ORDER, { appointmentId }),

  verify: (data) => api.post(API_ENDPOINTS.PAYMENTS.VERIFY, data),

  getHistory: () => api.get(API_ENDPOINTS.PAYMENTS.HISTORY),

  getById: (id) => api.get(API_ENDPOINTS.PAYMENTS.BY_ID(id)),

  getByAppointment: (appointmentId) =>
    api.get(API_ENDPOINTS.PAYMENTS.BY_APPOINTMENT(appointmentId)),
};

export default paymentService;
