import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

import {
  acceptConsent,
  getTerms,
  getPrivacyPolicy,
} from '../services/consentService'

function Consent() {

  const navigate = useNavigate()

  const [termsAccepted, setTermsAccepted] = useState(false)
  const [privacyAccepted, setPrivacyAccepted] = useState(false)

  const [terms, setTerms] = useState('')
  const [privacyPolicy, setPrivacyPolicy] = useState('')

  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  async function handleViewTerms() {

    try {

      const content = await getTerms()

      setTerms(content)

    } catch (error) {

      setError(error.message)
    }
  }

  async function handleViewPrivacyPolicy() {

    try {

      const content = await getPrivacyPolicy()

      setPrivacyPolicy(content)

    } catch (error) {

      setError(error.message)
    }
  }

  async function handleSubmit(event) {

    event.preventDefault()

    if (!termsAccepted || !privacyAccepted) {

      setError(
        'Você precisa aceitar os Termos de Uso e a Política de Privacidade.'
      )

      return
    }

    try {

      setLoading(true)
      setError('')

      await acceptConsent()

      navigate('/dashboard')

    } catch (error) {

      setError(error.message)

    } finally {

      setLoading(false)
    }
  }

  return (
    <div>

      <h1>Termos e Privacidade</h1>

      <button
        type="button"
        onClick={handleViewTerms}
      >
        Ver Termos de Uso
      </button>

      <button
        type="button"
        onClick={handleViewPrivacyPolicy}
      >
        Ver Política de Privacidade
      </button>

      {terms && (
        <section>
          <h2>Termos de Uso</h2>

          <pre>
            {terms}
          </pre>
        </section>
      )}

      {privacyPolicy && (
        <section>
          <h2>Política de Privacidade</h2>

          <pre>
            {privacyPolicy}
          </pre>
        </section>
      )}

      <form onSubmit={handleSubmit}>

        <div>
          <label>
            <input
              type="checkbox"
              checked={termsAccepted}
              onChange={(event) =>
                setTermsAccepted(event.target.checked)
              }
            />

            Aceito os Termos de Uso
          </label>
        </div>

        <div>
          <label>
            <input
              type="checkbox"
              checked={privacyAccepted}
              onChange={(event) =>
                setPrivacyAccepted(event.target.checked)
              }
            />

            Aceito a Política de Privacidade
          </label>
        </div>

        {error && (
          <p>{error}</p>
        )}

        <button
          type="submit"
          disabled={loading}
        >
          {loading ? 'Salvando...' : 'Continuar'}
        </button>

      </form>

    </div>
  )
}

export default Consent