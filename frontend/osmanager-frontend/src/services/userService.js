import { apiFetch } from './api'

export async function checkConsent() {

  const response = await apiFetch('/api/users/consent')

  if (!response.ok) {
    throw new Error('Não foi possível verificar o consentimento')
  }

  return response.json()
}