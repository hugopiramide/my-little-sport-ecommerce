import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { authService } from '../services/AuthService'
import { type RegisterRequest, type LoginRequest } from '../../shared/types'

const Register = () => {
  const navigate = useNavigate()

  const [formData, setFormData] = useState<RegisterRequest & { confirmPassword?: string }>({
    username: '',
    name: '',
    surname: '',
    birthday: '',
    email: '',
    password: '',
    confirmPassword: '',
    profileImgUrl: ''
  })

  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev: any) => ({
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

    if (!formData.username || !formData.name || !formData.email || !formData.password || !formData.birthday) {
      setError('Please fill in all required fields')
      setLoading(false)
      return
    }

    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match')
      setLoading(false)
      return
    }

    const birthDate = new Date(formData.birthday)
    if (isNaN(birthDate.getTime())) {
      setError('Invalid birth date')
      setLoading(false)
      return
    }

    if (!isAdult(formData.birthday)) {
      setError('You must be over 18 years old to register')
      setLoading(false)
      return
    }

    try {
      const { confirmPassword, ...submissionDataPayload } = formData
      const submissionData: RegisterRequest = {
        ...submissionDataPayload,
        birthday: new Date(formData.birthday)
      }
      const newUserResponse = await authService.register(submissionData)

      if (newUserResponse.requiresVerification) {
        navigate(`/verify-email?username=${formData.username}&email=${formData.email}`)
      } else {
        const loginCredentials: LoginRequest = {
          username: formData.username,
          password: formData.password
        }

        const authResponse = await authService.login(loginCredentials)
        navigate('/home', { state: { message: 'Welcome, ' + authResponse.user.username + '!' } })
      }
    } catch (err) {
      const msg = err instanceof Error ? err.message : 'Unknown error'
      if (msg.includes('Username already exists')) {
        setError('That username is already taken. Please choose another one.')
      } else if (msg.includes('Email already exists')) {
        setError('That email is already registered. Please log in or use a different email.')
      } else {
        setError('Could not create account. Please try again.')
      }
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="container-fluid min-vh-100 bg-white d-flex flex-column align-items-center justify-content-center py-5">
      <div className="row w-100 justify-content-center px-3">
        <div className="col-12 col-md-8 col-lg-5 col-xl-4 text-center">
          <Link to="/" className="d-inline-block mb-4 no-underline text-black hover:opacity-80 transition-opacity">
            <h3 className="fw-black mb-0 tracking-tight-15 text-dark uppercase">
              MYLITTLESPORT
            </h3>
          </Link>

          <h1 className="mb-5">
            JOIN US
          </h1>

          {error && (
            <div className="alert alert-danger small py-3 mb-4" role="alert">
              {error}
            </div>
          )}

          <form className="d-flex flex-column gap-3" onSubmit={handleSubmit}>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              placeholder="Username"
              className="form-control-custom w-100 py-3"
              required
            />

            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="First Name"
              className="form-control-custom w-100 py-3"
              required
            />

            <input
              type="text"
              name="surname"
              value={formData.surname}
              onChange={handleChange}
              placeholder="Last Name (Optional)"
              className="form-control-custom w-100 py-3"
            />

            <input
              type="date"
              name="birthday"
              value={formData.birthday as string}
              onChange={handleChange}
              placeholder="BirthDate"
              className="form-control-custom w-100 py-3"
              required
            />

            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Email Address"
              className="form-control-custom w-100 py-3"
              required
            />

            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Password"
              className="form-control-custom w-100 py-3"
              required
            />

            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              placeholder="Confirm Password"
              className="form-control-custom w-100 py-3"
              required
            />

            <input
              type="text"
              name="profileImgUrl"
              value={formData.profileImgUrl}
              onChange={handleChange}
              placeholder="Profile Picture URL (Optional)"
              className="form-control-custom w-100 py-3"
            />

            <p className="small text-muted text-center my-4 uppercase">
              By registering, you agree to our 
              <Link to="#" className="text-dark fw-bold mx-1">Privacy Policy</Link> 
              and 
              <Link to="#" className="text-dark fw-bold mx-1">Terms of Use</Link>
            </p>

            <button
              type="submit"
              disabled={loading}
              className={`btn-dark-custom w-100 py-3 fw-black text-uppercase ${loading ? 'opacity-70' : ''}`}
            >
              {loading ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Registering...
                </>
              ) : (
                'REGISTER'
              )}
            </button>
          </form>

          <div className="mt-4 text-center">
            <p className="text-muted small">
              Already a member? 
              <Link
                to={'/login'}
                className="ms-1 fw-bold text-dark text-decoration-underline"
              >
                Log In
              </Link>
            </p>
          </div>

          <div className="mt-3 text-center">
            <Link
              to={'/'}
              className="text-dark small fw-bold text-decoration-underline"
            >
              Back to home
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Register