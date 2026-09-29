import { apiFetch } from './api'

export async function acceptConsent() {

  const response = await apiFetch('/api/users/consent', {
    method: 'POST',
    body: JSON.stringify({
      termsAccepted: true,
      privacyPolicyAccepted: true,
    }),
  })

  if (!response.ok) {
    throw new Error('Não foi possível registrar o consentimento')
  }
}

export async function getTerms() {

  const response = await apiFetch('/api/public/terms')

  if (!response.ok) {
    throw new Error('Não foi possível carregar os Termos de Uso')
  }

  return response.text()
}

export async function getPrivacyPolicy() {

  const response = await apiFetch('/api/public/privacy-policy')

  if (!response.ok) {
    throw new Error(
      'Não foi possível carregar a Política de Privacidade'
    )
  }

  return response.text()
}