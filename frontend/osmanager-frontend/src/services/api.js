const API_URL = 'http://localhost:8080'

export async function apiFetch(endpoint, options = {}) {

  const response = await fetch(
    `${API_URL}${endpoint}`,
    {
      ...options,
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
    }
  )

  return response
}