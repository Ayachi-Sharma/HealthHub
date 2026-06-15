import { createContext, useContext, useState, useEffect, useCallback } from 'react';
import { STORAGE_KEYS } from '../utils/constants';
import { clearAuthStorage, getStoredUser } from '../utils/helpers';
import authService from '../services/authService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const storedUser = getStoredUser();
    const token = localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN);

    if (storedUser && token) {
      setUser(storedUser);
      setIsAuthenticated(true);
    }
    setLoading(false);
  }, []);

  const login = useCallback(async (credentials) => {
    const response = await authService.login(credentials);
    const authData = response.data.data;

    const userData = {
      email: authData.email,
      role: authData.role,
      userId: authData.userId,
    };

    localStorage.setItem(STORAGE_KEYS.ACCESS_TOKEN, authData.accessToken);
    localStorage.setItem(STORAGE_KEYS.REFRESH_TOKEN, authData.refreshToken);
    localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(userData));

    setUser(userData);
    setIsAuthenticated(true);

    return authData;
  }, []);

  const registerPatient = useCallback(async (data) => {
    const response = await authService.registerPatient(data);
    const authData = response.data.data;

    const userData = {
      email: authData.email,
      role: authData.role,
      userId: authData.userId,
    };

    localStorage.setItem(STORAGE_KEYS.ACCESS_TOKEN, authData.accessToken);
    localStorage.setItem(STORAGE_KEYS.REFRESH_TOKEN, authData.refreshToken);
    localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(userData));

    setUser(userData);
    setIsAuthenticated(true);

    return authData;
  }, []);

  const registerDoctor = useCallback(async (data) => {
    const response = await authService.registerDoctor(data);
    const authData = response.data.data;

    const userData = {
      email: authData.email,
      role: authData.role,
      userId: authData.userId,
    };

    localStorage.setItem(STORAGE_KEYS.ACCESS_TOKEN, authData.accessToken);
    localStorage.setItem(STORAGE_KEYS.REFRESH_TOKEN, authData.refreshToken);
    localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(userData));

    setUser(userData);
    setIsAuthenticated(true);

    return authData;
  }, []);

  const logout = useCallback(() => {
    clearAuthStorage();
    setUser(null);
    setIsAuthenticated(false);
  }, []);

  const hasRole = useCallback(
    (role) => user?.role === role,
    [user]
  );

  const value = {
    user,
    loading,
    isAuthenticated,
    login,
    registerPatient,
    registerDoctor,
    logout,
    hasRole,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuthContext = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuthContext must be used within an AuthProvider');
  }
  return context;
};

export default AuthContext;
