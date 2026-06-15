import { Link } from 'react-router-dom';
import { APP_NAME, ROUTES } from '../../utils/constants';
import useAuth from '../../hooks/useAuth';

const Header = () => {
  const { isAuthenticated } = useAuth();

  return (
    <header className="bg-white shadow-sm border-b border-gray-100">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <Link to={ROUTES.HOME} className="flex items-center gap-2">
            <div className="w-9 h-9 bg-primary-600 rounded-lg flex items-center justify-center">
              <span className="text-white font-bold text-lg">M</span>
            </div>
            <span className="text-xl font-bold text-gray-900">{APP_NAME}</span>
          </Link>

          <div className="flex items-center gap-3">
            {!isAuthenticated ? (
              <>
                <Link
                  to={ROUTES.LOGIN}
                  className="text-gray-600 hover:text-primary-600 font-medium transition-colors"
                >
                  Login
                </Link>
                <Link
                  to={ROUTES.REGISTER_PATIENT}
                  className="bg-primary-600 text-white px-4 py-2 rounded-lg hover:bg-primary-700 transition-colors font-medium"
                >
                  Get Started
                </Link>
              </>
            ) : null}
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
