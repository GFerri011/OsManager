import { apiFetch } from './api'

export async function createTicket(ticketData) {
  const response = await apiFetch('/api/tickets', {
    method: 'POST',
    body: JSON.stringify(ticketData),
  })

  if (!response.ok) {
    throw new Error('Não foi possível criar o chamado')
  }

  return response.json()
}

export async function getMyTickets() {
  const response = await apiFetch('/api/tickets')

  if (!response.ok) {
    throw new Error('Não foi possível carregar seus chamados')
  }

  return response.json()
}