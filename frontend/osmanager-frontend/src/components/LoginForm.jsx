import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

import { login } from '../services/authService'
import { checkConsent } from '../services/userService'

function LoginForm() {

  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  const navigate = useNavigate()

  async function handleSubmit(event) {

    event.preventDefault()

    setError('')

    try {

      await login(email, password)

      const consentAccepted = await checkConsent()

      if (consentAccepted) {

        navigate('/dashboard')

      } else {

        navigate('/consent')

      }

    } catch (error) {

      setError(error.message)

    }
  }

  return (
    <form onSubmit={handleSubmit}>

      <div>
        <label htmlFor="email">
          E-mail
        </label>

        <input
          id="email"
          type="email"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
        />
      </div>

      <div>
        <label htmlFor="password">
          Senha
        </label>

        <input
          id="password"
          type="password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />
      </div>

      {error && (
        <p>{error}</p>
      )}

      <button type="submit">
        Entrar
      </button>

    </form>
  )
}

export default LoginForm