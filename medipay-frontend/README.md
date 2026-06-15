# MediPay Frontend

React frontend for the MediPay healthcare appointment and payment platform.

## Tech Stack

- **React 19** with Vite
- **React Router DOM** for navigation
- **Context API** for state management
- **Axios** for API communication
- **Tailwind CSS v4** for styling

## Prerequisites

- Node.js 18+
- npm 9+
- MediPay backend running on `http://localhost:8080`

## Getting Started

1. **Install dependencies**
   ```bash
   npm install
   ```

2. **Configure environment**
   ```bash
   cp .env.example .env
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```

   The app runs at `http://localhost:5173`

4. **Build for production**
   ```bash
   npm run build
   ```

## Project Structure

```
src/
├── assets/          # Images and icons
├── components/
│   └── common/      # Shared UI components (Header, Footer, Layout, etc.)
├── context/         # React Context providers (Auth, App)
├── hooks/           # Custom React hooks
├── pages/           # Page components
├── routes/          # Route configuration
├── services/        # API service layer
└── utils/           # Constants, helpers, validators
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `VITE_API_BASE_URL` | Backend API base URL | `http://localhost:8080/api` |
| `VITE_APP_NAME` | Application display name | `MediPay` |
| `VITE_RAZORPAY_KEY_ID` | Razorpay public key (Phase 13) | - |

## Phase Status

- **Phase 8** (Current): Frontend setup complete
- **Phase 9**: Authentication UI (Login, Register pages)
- **Phase 10**: Patient module UI
- **Phase 11**: Doctor module UI
- **Phase 12**: Admin module UI
- **Phase 13**: Payment UI

## API Integration

All API calls go through the centralized Axios instance in `src/services/api.js` with:
- Automatic JWT token attachment
- Token refresh on 401 responses
- Consistent error handling

## Available Scripts

| Command | Description |
|---------|-------------|
| `npm run dev` | Start development server |
| `npm run build` | Build for production |
| `npm run preview` | Preview production build |
| `npm run lint` | Run ESLint |
