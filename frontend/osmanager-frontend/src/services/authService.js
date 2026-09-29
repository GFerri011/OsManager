import { apiFetch } from './api'

export async function login(email, password) {
  const response = await apiFetch('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify({ email, password }),
  })

  if (!response.ok) {
    throw new Error('E-mail ou senha inválidos')
  }

  return response
}

export async function getCurrentUser() {
  const response = await apiFetch('/api/auth/me')

  if (!response.ok) {
    return null
  }

  return response.json()
}

export async function logout() {
  const response = await apiFetch('/api/auth/logout', {
    method: 'POST',
  })

  if (!response.ok) {
    throw new Error('Não foi possível encerrar a sessão')
  }
}