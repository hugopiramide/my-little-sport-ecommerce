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
      const authResponse = await authService.login(credentials)
      
      if (authResponse.requiresVerification) {
        navigate(`/verify-email?username=${credentials.username}&email=${authResponse.user.email}`)
      } else {
        navigate('/home', { state: { message: 'Welcome back, ' + authResponse.user.username + '!' } })
      }
    } catch (err) {
      setError('Invalid username or password. Please try again. ' + (err instanceof Error ? err.message : 'Unknown error'))
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

          <h1 className="mb-5 lh-1 tracking-tighter">
            YOUR <br />
            ACCOUNT FOR <br />
            EVERYTHING
          </h1>

          {error && (
            <div className="alert alert-danger small py-3 mb-4 text-center" role="alert">
              {error}
            </div>
          )}

          <form className="d-flex flex-column gap-3 mb-4" onSubmit={handleSubmit}>
            <div className="form-group">
              <input
                type="text"
                name="username"
                value={credentials.username}
                onChange={handleChange}
                placeholder="Username"
                className="form-control-custom w-100 py-3"
                required
              />
            </div>

            <div className="form-group">
              <input
                type="password"
                name="password"
                value={credentials.password}
                onChange={handleChange}
                placeholder="Password"
                className="form-control-custom w-100 py-3"
                required
              />
            </div>

            <div className="text-center my-4">
              <p className="text-muted small mb-0">
                Problems with your Password? <Link to="#" className="text-dark fw-bold text-decoration-underline">Restore-Password</Link>
              </p>
            </div>

            <button
              type="submit"
              disabled={loading}
              className={`btn-dark-custom w-100 py-3 fw-black text-uppercase ${
                loading ? 'opacity-70 cursor-not-allowed' : 'active:scale-95'
              }`}
            >
              {loading ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Entering...
                </>
              ) : (
                'LOG IN'
              )}
            </button>
          </form>

          <div className="text-center mb-4">
            <p className="text-muted small">
              Don't have account? 
              <Link
                to={'/register'}
                className="ms-1 fw-bold text-dark text-decoration-underline"
              >
                Register
              </Link>
            </p>
          </div>

          <div className="text-center">
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

export default LogIn