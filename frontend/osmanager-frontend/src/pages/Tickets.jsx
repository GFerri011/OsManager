import { useEffect, useState } from 'react'
import { getMyTickets } from '../services/ticketService'

function Tickets() {
  const [tickets, setTickets] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadTickets() {
      try {
        const data = await getMyTickets()
        setTickets(data)
      } catch (error) {
        setError(error.message)
      } finally {
        setLoading(false)
      }
    }

    loadTickets()
  }, [])

  if (loading) {
    return <p>Carregando chamados...</p>
  }

  if (error) {
    return <p>{error}</p>
  }

  return (
    <div>
      <h2>Meus Chamados</h2>

      {tickets.length === 0 ? (
        <p>Você ainda não possui chamados.</p>
      ) : (
        <ul>
          {tickets.map((ticket) => (
            <li key={ticket.id}>
              <strong>{ticket.protocol}</strong>
              <p>{ticket.title}</p>
              <p>Status: {ticket.status}</p>
              <p>Prioridade: {ticket.priority}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}

export default Tickets