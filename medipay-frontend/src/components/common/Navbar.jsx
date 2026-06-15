import { Link } from 'react-router-dom';
import { APP_NAME, ROUTES } from '../../utils/constants';
import useAuth from '../../hooks/useAuth';
import { getRoleDashboardPath } from '../../utils/helpers';

const Navbar = () => {
  const { isAuthenticated, user, logout } = useAuth();

  if (!isAuthenticated) return null;

  const dashboardPath = getRoleDashboardPath(user?.role);

  return (
    <nav className="bg-primary-700 text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-12">
          <div className="flex items-center gap-6">
            <Link to={dashboardPath} className="text-sm font-medium hover:text-primary-200 transition-colors">
              Dashboard
            </Link>
            {user?.role === 'ROLE_PATIENT' && (
              <Link to="/patient/doctors" className="text-sm font-medium hover:text-primary-200 transition-colors">
                Find Doctors
              </Link>
            )}
            {user?.role === 'ROLE_DOCTOR' && (
              <Link to="/doctor/slots" className="text-sm font-medium hover:text-primary-200 transition-colors">
                Manage Slots
              </Link>
            )}
            {user?.role === 'ROLE_ADMIN' && (
              <Link to="/admin/users" className="text-sm font-medium hover:text-primary-200 transition-colors">
                Manage Users
              </Link>
            )}
          </div>

          <div className="flex items-center gap-4">
            <span className="text-sm text-primary-200">{user?.email}</span>
            <button
              onClick={logout}
              className="text-sm font-medium hover:text-primary-200 transition-colors"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
