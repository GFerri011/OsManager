import { useEffect, useState } from 'react'
import { Navigate } from 'react-router-dom'
import { getCurrentUser } from '../services/authService'

function ProtectedRoute({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    async function checkAuthentication() {
      const currentUser = await getCurrentUser()

      setUser(currentUser)
      setLoading(false)
    }

    checkAuthentication()
  }, [])

  if (loading) {
    return <p>Verificando autenticação...</p>
  }

  if (!user) {
    return <Navigate to="/" replace />
  }

  return children
}

export default ProtectedRoute