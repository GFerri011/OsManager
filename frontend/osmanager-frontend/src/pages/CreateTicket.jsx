import { useState } from 'react'
import { createTicket } from '../services/ticketService'

function CreateTicket() {
    const [title, setTitle] = useState('')
    const [description, setDescription] = useState('')

    const [category, setCategory] = useState('')
    const [priority, setPriority] = useState('')

    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)
    const [success, setSuccess] = useState('')

    async function handleSubmit(event) {
        event.preventDefault()

        setError('')
        setSuccess('')

        if (!title.trim()) {
            setError('O título é obrigatório.')
            return
        }

        if (!description.trim()) {
            setError('A descrição é obrigatória.')
            return
        }

        if (!category) {
            setError('Selecione uma categoria.')
            return
        }

        if (!priority) {
            setError('Selecione uma prioridade.')
            return
        }

        try {
            setLoading(true)

            const ticket = await createTicket({
                title,
                description,
                category,
                priority,
            })

            setSuccess(`Chamado ${ticket.protocol} criado com sucesso.`)

            setTitle('')
            setDescription('')
            setCategory('')
            setPriority('')
        } catch (error) {
            setError(error.message)
        } finally {
            setLoading(false)
        }
    }
    return (
        <div>
            <h2>Novo Chamado</h2>

            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="title">Título</label>

                    <input
                        id="title"
                        type="text"
                        value={title}
                        onChange={(event) => setTitle(event.target.value)}
                    />
                </div>

                <div>
                    <label htmlFor="description">Descrição</label>

                    <textarea
                        id="description"
                        value={description}
                        onChange={(event) => setDescription(event.target.value)}
                    />
                </div>

                <div>
                    <label htmlFor="category">Categoria</label>

                    <select
                        id="category"
                        value={category}
                        onChange={(event) => setCategory(event.target.value)}
                    >
                        <option value="">Selecione uma categoria</option>
                        <option value="HARDWARE">Hardware</option>
                        <option value="SOFTWARE">Software</option>
                        <option value="ACESSO">Acesso</option>
                        <option value="REDE">Rede</option>
                        <option value="EMAIL">E-mail</option>
                        <option value="SISTEMA">Sistema</option>
                        <option value="IMPRESSAO">Impressão</option>
                        <option value="SEGURANCA">Segurança</option>
                        <option value="TELEFONIA">Telefonia</option>
                        <option value="OUTROS">Outros</option>
                    </select>
                </div>

                <div>
                    <label htmlFor="priority">Prioridade</label>

                    <select
                        id="priority"
                        value={priority}
                        onChange={(event) => setPriority(event.target.value)}
                    >
                        <option value="">Selecione uma prioridade</option>
                        <option value="BAIXA">Baixa</option>
                        <option value="MEDIA">Média</option>
                        <option value="ALTA">Alta</option>
                        <option value="CRITICA">Crítica</option>
                    </select>
                </div>
                {error && <p>{error}</p>}

                {success && <p>{success}</p>}
                <button type="submit" disabled={loading}>
                    {loading ? 'Criando chamado...' : 'Criar chamado'}
                </button>
            </form>
        </div>
    )
}

export default CreateTicket