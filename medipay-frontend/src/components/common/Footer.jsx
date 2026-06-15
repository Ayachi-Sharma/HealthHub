import { APP_NAME } from '../../utils/constants';

const Footer = () => {
  return (
    <footer className="bg-gray-900 text-gray-300 mt-auto">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div>
            <h3 className="text-white font-bold text-lg mb-3">{APP_NAME}</h3>
            <p className="text-sm text-gray-400">
              A comprehensive healthcare platform for booking appointments and secure online payments.
            </p>
          </div>

          <div>
            <h4 className="text-white font-semibold mb-3">Quick Links</h4>
            <ul className="space-y-2 text-sm">
              <li><a href="/" className="hover:text-primary-400 transition-colors">Home</a></li>
              <li><a href="/login" className="hover:text-primary-400 transition-colors">Login</a></li>
              <li><a href="/register/patient" className="hover:text-primary-400 transition-colors">Register as Patient</a></li>
              <li><a href="/register/doctor" className="hover:text-primary-400 transition-colors">Register as Doctor</a></li>
            </ul>
          </div>

          <div>
            <h4 className="text-white font-semibold mb-3">Contact</h4>
            <ul className="space-y-2 text-sm text-gray-400">
              <li>support@medipay.com</li>
              <li>+91 1800-MEDIPAY</li>
            </ul>
          </div>
        </div>

        <div className="border-t border-gray-800 mt-8 pt-6 text-center text-sm text-gray-500">
          &copy; {new Date().getFullYear()} {APP_NAME}. All rights reserved.
        </div>
      </div>
    </footer>
  );
};

export default Footer;
