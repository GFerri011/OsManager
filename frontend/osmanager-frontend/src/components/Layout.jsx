import { Link, Outlet } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { logout } from '../services/authService'

function Layout() {

    const navigate = useNavigate()

    async function handleLogout() {
        try {
            await logout()
            navigate('/')
        } catch (error) {
            console.error(error)
        }
    }
    return (
        <div>

            <header>
                <h1>OS Manager</h1>

                <nav>
                    <Link to="/dashboard">
                        Dashboard
                    </Link>

                    <Link to="/tickets">
                        Meus Chamados
                    </Link>

                    <Link to="/tickets/new">
                        Novo Chamado
                    </Link>
                </nav>
                <button type="button" onClick={handleLogout}>
                    Sair
                </button>
            </header>

            <main>
                <Outlet />
            </main>

        </div>
    )
}

export default Layout