import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { authService } from '../services/AuthService'
import { type RegisterRequest, type LoginRequest } from '../../shared/types'

const Register = () => {
  const navigate = useNavigate()

  const [formData, setFormData] = useState<RegisterRequest>({
    username: '',
    name: '',
    surname: '',
    birthday: '',
    email: '',
    password: '',
    profileImgUrl: ''
  })

  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev: RegisterRequest) => ({
      ...prev,
      [name]: value
    }))
  }

  const isAdult = (birthDateString: string | Date): boolean => {
    if (!birthDateString) return false
    const birthDate = new Date(birthDateString)
    if (isNaN(birthDate.getTime())) return false

    const today = new Date()
    const age = today.getFullYear() - birthDate.getFullYear()
    const monthDiff = today.getMonth() - birthDate.getMonth()

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
      return age - 1 >= 18
    }

    return age >= 18
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    setLoading(true)

    if (!formData.username || !formData.name || !formData.surname || !formData.email || !formData.password || !formData.birthday) {
      setError('Por favor, rellena todos los campos obligatorios')
      setLoading(false)
      return
    }

    // Date validation
    const birthDate = new Date(formData.birthday)
    if (isNaN(birthDate.getTime())) {
      setError('La fecha de nacimiento no es válida')
      setLoading(false)
      return
    }

    if (!isAdult(formData.birthday)) {
      setError('Debes ser mayor de 18 años para registrarte')
      setLoading(false)
      return
    }

    try {
      const submissionData: RegisterRequest = {
        ...formData,
        birthday: new Date(formData.birthday)
      }
      const newUser = await authService.register(submissionData)

      const loginCredentials: LoginRequest = {
        username: formData.username,
        password: formData.password
      }

      const user = await authService.login(loginCredentials)

      localStorage.setItem('user', JSON.stringify(user))

      navigate('/home', { state: { message: '¡Bienvenido, ' + newUser.username + '!' } })
    } catch (err) {
      setError('No se pudo crear la cuenta. Inténtalo de nuevo. ' + (err instanceof Error ? err.message : 'Error desconocido'))
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
          ÚNETE A NOSOTROS
        </h1>

        {error && <p className="text-red-500 text-sm mb-4">{error}</p>}

        <form className="flex flex-col gap-4 text-center" onSubmit={handleSubmit}>
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            placeholder="UserName"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
            required
          />

          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Nombre"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
            required
          />

          <input
            type="text"
            name="surname"
            value={formData.surname}
            onChange={handleChange}
            placeholder="Apellidos"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
            required
          />

          <input
            type="date"
            name="birthday"
            value={formData.birthday as string}
            onChange={handleChange}
            placeholder="Fecha de Nacimiento"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
            required
          />

          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Electronic Email"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
            required
          />

          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Password"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
            required
          />

          <input
            type="text"
            name="profileImgUrl"
            value={formData.profileImgUrl}
            onChange={handleChange}
            placeholder="Profile Picture URL"
            className="w-full border border-gray-300 p-3 rounded-sm focus:border-black outline-none transition-all"
          />

          <p className="text-[12px] text-gray-500 text-center leading-5 px-4 mt-3">
            Registrándote aceptas la
            <span className="underline text-black font-semibold"> HCD Política de Privacidad y Términos de Uso</span>
          </p>

          <button
            type="submit"
            disabled={loading}
            className={`bg-black text-white font-bold py-3 mt-4 rounded-sm hover:bg-zinc-800 transition-colors uppercase tracking-tight p-5 ${loading ? 'opacity-50' : ''}`}
          >
            {loading ? 'Cargando...' : 'Register'}
          </button>
        </form>

        <div className="mt-6 text-center text-sm">
          <span className="text-gray-500 me-3">¿Ya eres miembro?</span>
          <Link
            to={'/login'}
            className="ml-1 font-bold underline hover:text-gray-600 transition-colors"
          >
            Iniciar Sesión
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

export default Register