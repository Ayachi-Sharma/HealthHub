export const isValidEmail = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
};

export const isValidPassword = (password) => {
  return password && password.length >= 6;
};

export const isValidPhone = (phone) => {
  const phoneRegex = /^[6-9]\d{9}$/;
  return phoneRegex.test(phone);
};

export const isRequired = (value) => {
  return value !== null && value !== undefined && String(value).trim() !== '';
};

export const validateLoginForm = ({ email, password }) => {
  const errors = {};

  if (!isRequired(email)) {
    errors.email = 'Email is required';
  } else if (!isValidEmail(email)) {
    errors.email = 'Please enter a valid email';
  }

  if (!isRequired(password)) {
    errors.password = 'Password is required';
  } else if (!isValidPassword(password)) {
    errors.password = 'Password must be at least 6 characters';
  }

  return errors;
};

export const validatePatientRegisterForm = (form) => {
  const errors = {};

  if (!isRequired(form.fullName)) errors.fullName = 'Full name is required';
  if (!isRequired(form.email)) {
    errors.email = 'Email is required';
  } else if (!isValidEmail(form.email)) {
    errors.email = 'Please enter a valid email';
  }
  if (!isRequired(form.password)) {
    errors.password = 'Password is required';
  } else if (!isValidPassword(form.password)) {
    errors.password = 'Password must be at least 6 characters';
  }
  if (!isRequired(form.phone)) {
    errors.phone = 'Phone number is required';
  } else if (!isValidPhone(form.phone)) {
    errors.phone = 'Please enter a valid 10-digit phone number';
  }
  if (!isRequired(form.gender)) errors.gender = 'Gender is required';

  return errors;
};
