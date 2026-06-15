import { Routes, Route } from 'react-router-dom';
import Layout from '../components/common/Layout';
import ProtectedRoute from '../components/common/ProtectedRoute';
import Home from '../pages/Home';
import { ROLES } from '../utils/constants';

const PlaceholderPage = ({ title, description }) => (
  <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
    <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-8 text-center">
      <h1 className="text-2xl font-bold text-gray-900 mb-2">{title}</h1>
      <p className="text-gray-500">{description}</p>
    </div>
  </div>
);

const AppRoutes = () => {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <Layout>
            <Home />
          </Layout>
        }
      />

      <Route
        path="/login"
        element={
          <Layout>
            <PlaceholderPage title="Login" description="Coming in Phase 9 - Authentication UI" />
          </Layout>
        }
      />

      <Route
        path="/register/patient"
        element={
          <Layout>
            <PlaceholderPage title="Patient Registration" description="Coming in Phase 9 - Authentication UI" />
          </Layout>
        }
      />

      <Route
        path="/register/doctor"
        element={
          <Layout>
            <PlaceholderPage title="Doctor Registration" description="Coming in Phase 9 - Authentication UI" />
          </Layout>
        }
      />

      <Route
        path="/patient/dashboard"
        element={
          <ProtectedRoute allowedRoles={[ROLES.PATIENT]}>
            <Layout>
              <PlaceholderPage title="Patient Dashboard" description="Coming in Phase 10 - Patient Module UI" />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/doctor/dashboard"
        element={
          <ProtectedRoute allowedRoles={[ROLES.DOCTOR]}>
            <Layout>
              <PlaceholderPage title="Doctor Dashboard" description="Coming in Phase 11 - Doctor Module UI" />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
        path="/admin/dashboard"
        element={
          <ProtectedRoute allowedRoles={[ROLES.ADMIN]}>
            <Layout>
              <PlaceholderPage title="Admin Dashboard" description="Coming in Phase 12 - Admin Module UI" />
            </Layout>
          </ProtectedRoute>
        }
      />

      <Route
        path="*"
        element={
          <Layout>
            <PlaceholderPage title="404 - Page Not Found" description="The page you are looking for does not exist." />
          </Layout>
        }
      />
    </Routes>
  );
};

export default AppRoutes;
