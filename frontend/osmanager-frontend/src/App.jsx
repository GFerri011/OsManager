import { BrowserRouter, Routes, Route } from 'react-router-dom'

import Login from './pages/Login'
import Consent from './pages/Consent'
import Dashboard from './pages/Dashboard'
import Tickets from './pages/Tickets'
import CreateTicket from './pages/CreateTicket'

import Layout from './components/Layout'
import ProtectedRoute from './routes/ProtectedRoute'

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/" element={<Login />} />

        <Route
          path="/consent"
          element={
            <ProtectedRoute>
              <Consent />
            </ProtectedRoute>
          }
        />

        <Route
          element={
            <ProtectedRoute>
              <Layout />
            </ProtectedRoute>
          }
        >
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/tickets" element={<Tickets />} />
          <Route path="/tickets/new" element={<CreateTicket />} />
        </Route>

      </Routes>
    </BrowserRouter>
  )
}

export default App