import { Link } from 'react-router-dom';
import { ROUTES } from '../utils/constants';
import authService from '../services/authService';
import { useState, useEffect } from 'react';

const Home = () => {
  const [apiStatus, setApiStatus] = useState(null);

  useEffect(() => {
    authService
      .test()
      .then((res) => setApiStatus(res.data.message))
      .catch(() => setApiStatus('Backend offline'));
  }, []);

  return (
    <div>
      <section className="bg-gradient-to-br from-primary-600 to-secondary-700 text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20">
          <div className="max-w-2xl">
            <h1 className="text-4xl sm:text-5xl font-bold mb-6">
              Healthcare Made Simple
            </h1>
            <p className="text-lg text-primary-100 mb-8">
              Book doctor appointments, manage your health records, and make secure
              online payments — all in one platform.
            </p>
            <div className="flex flex-wrap gap-4">
              <Link
                to={ROUTES.REGISTER_PATIENT}
                className="bg-white text-primary-700 px-6 py-3 rounded-lg font-semibold hover:bg-primary-50 transition-colors"
              >
                Register as Patient
              </Link>
              <Link
                to={ROUTES.REGISTER_DOCTOR}
                className="border-2 border-white text-white px-6 py-3 rounded-lg font-semibold hover:bg-white/10 transition-colors"
              >
                Register as Doctor
              </Link>
            </div>
          </div>
        </div>
      </section>

      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <h2 className="text-2xl font-bold text-gray-900 text-center mb-12">
          Why Choose MediPay?
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {[
            {
              title: 'Easy Booking',
              description: 'Find doctors by specialization and book appointments in minutes.',
              icon: '📅',
            },
            {
              title: 'Secure Payments',
              description: 'Pay consultation fees securely via Razorpay integration.',
              icon: '💳',
            },
            {
              title: 'Role-Based Access',
              description: 'Dedicated dashboards for patients, doctors, and administrators.',
              icon: '🔐',
            },
          ].map((feature) => (
            <div
              key={feature.title}
              className="bg-white rounded-xl shadow-sm border border-gray-100 p-6 text-center hover:shadow-md transition-shadow"
            >
              <div className="text-4xl mb-4">{feature.icon}</div>
              <h3 className="text-lg font-semibold text-gray-900 mb-2">{feature.title}</h3>
              <p className="text-gray-500 text-sm">{feature.description}</p>
            </div>
          ))}
        </div>
      </section>

      {apiStatus && (
        <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pb-12">
          <div className="bg-white rounded-lg border border-gray-200 p-4 flex items-center gap-3">
            <div
              className={`w-3 h-3 rounded-full ${
                apiStatus === 'Backend offline' ? 'bg-red-500' : 'bg-green-500'
              }`}
            />
            <span className="text-sm text-gray-600">
              API Status: <strong>{apiStatus}</strong>
            </span>
          </div>
        </section>
      )}
    </div>
  );
};

export default Home;
