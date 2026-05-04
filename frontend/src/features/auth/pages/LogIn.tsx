import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { authService } from '../services/AuthService'
import { type LoginRequest } from '../../shared/types'

const LogIn = () => {
  const navigate = useNavigate()

  const [credentials, setCredentials] = useState<LoginRequest>({
    username: '',
    password: '',
  })

  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setCredentials((prev: LoginRequest) => ({
      ...prev,
      [name]: value,
    }))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    setLoading(true)

    try {
      const user = await authService.login(credentials)

      localStorage.setItem('user', JSON.stringify(user))

      navigate('/home', { state: { message: '¡Bienvenido de nuevo, ' + user.username + '!' } })
    } catch (err) {
      setError('Email o contraseña incorrectos. Inténtalo de nuevo. ' + (err instanceof Error ? err.message : 'Error desconocido'))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="container min-h-screen bg-white flex flex-col items-center px-4 pt-10 font-sans text-[#111111] text-center">
      <div className="mt-5">
        <Link className="navbar-brand" to="/">
          <img id="logo" src="/src/assets/img/logo.svg" alt="Logo" className="w-16" />
        </Link>
      </div>

      <div className="w-full max-w-[460px]">
        <h1 className="text-[28px] font-bold tracking-tighter leading-8 mb-3 text-center uppercase">
          TU CUENTA PARA TODO HCD
        </h1>

        {error && <p className="text-red-500 text-sm mb-4">{error}</p>}

        <form className="flex flex-col gap-4 text-center" onSubmit={handleSubmit}>
          <input
            type="text"
            name="username"
            value={credentials.username}
            onChange={handleChange}
            placeholder="Username"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
            required
          />

          <input
            type="password"
            name="password"
            value={credentials.password}
            onChange={handleChange}
            placeholder="Password"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
            required
          />

          <div className="flex justify-between items-center text-xs text-gray-500 my-2">
            <label className="flex items-center gap-2 cursor-pointer mt-3">
              <input type="checkbox" className="accent-black w-4 h-4 me-2" />
              Stay logged In
            </label>
          </div>

          <p className="text-[12px] text-gray-500 text-center leading-5 px-4 mt-3">
            Al iniciar sesión, aceptas la
            <span className="underline text-black font-semibold"> HCD Política de Privacidad y Términos de Uso</span>
          </p>

          <button
            type="submit"
            disabled={loading}
            className={`bg-black text-white font-bold py-3 mt-4 rounded-sm hover:bg-zinc-800 transition-colors uppercase tracking-tight p-5 ${loading ? 'opacity-50 cursor-not-allowed' : ''
              }`}
          >
            {loading ? 'Entrando...' : 'Log In'}
          </button>
        </form>

        <div className="mt-6 text-center text-sm">
          <span className="text-gray-500 me-3">
            ¿ Aún no eres miembro ?
          </span>
          <Link
            to={'/register'}
            className="ml-1 font-bold underline hover:text-gray-600 transition-colors"
          >
            Unirse
          </Link>
        </div>

        <div className="mt-6 text-center">
          <Link
            to={'/'}
            className="no-underline text-gray-400 hover:text-black text-xs transition-colors"
          >
            ← Volver al Inicio
          </Link>
        </div>
      </div>
    </div>
  )
}

export default LogIn