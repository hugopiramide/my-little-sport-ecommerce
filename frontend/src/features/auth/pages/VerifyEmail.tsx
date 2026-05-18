import React, { useMemo, useState } from 'react'
import { Link, useNavigate, useSearchParams } from 'react-router-dom'
import { authService } from '../services/AuthService'
import { type ResendVerificationRequest, type VerifyEmailRequest } from '../../shared/types'
import { getCurrentUser } from '../../auth/utils/authUtils'

const VerifyEmail = () => {
  const navigate = useNavigate()
  const [searchParams] = useSearchParams()

  const username = useMemo(() => searchParams.get('username') ?? '', [searchParams])
  const email = useMemo(() => searchParams.get('email') ?? '', [searchParams])

  const [code, setCode] = useState('')
  const [loading, setLoading] = useState(false)
  const [resending, setResending] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [info, setInfo] = useState<string | null>(null)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    setInfo(null)

    if (!username) {
      setError('Missing username. Please go back and register again.')
      return
    }
    if (!code || !/^\d{6}$/.test(code)) {
      setError('The code must be exactly 6 digits')
      return
    }

    setLoading(true)
    try {
      const payload: VerifyEmailRequest = { username, code }
      await authService.verifyEmail(payload)
      const user = getCurrentUser()
      navigate('/home', { state: { message: 'Welcome, ' + (user?.username || 'back') + '!' } })
    } catch (err: any) {
      setError(err?.message || 'Invalid or expired verification code')
    } finally {
      setLoading(false)
    }
  }

  const handleResend = async () => {
    if (!username) return
    setError(null)
    setInfo(null)
    setResending(true)

    try {
      const payload: ResendVerificationRequest = { username }
      await authService.resendVerificationCode(payload)
      setInfo('A new code has been sent. Please check your email.')
    } catch (err: any) {
      setError(err?.message || 'Could not resend the code')
    } finally {
      setResending(false)
    }
  }

  return (
    <div className="container-fluid min-vh-100 bg-white d-flex flex-column align-items-center justify-content-center py-5">
      <div className="row w-100 justify-content-center px-3">
        <div className="col-12 col-md-8 col-lg-5 col-xl-4 text-center">
          <Link to="/" className="d-inline-block mb-5 no-underline text-black hover:opacity-80 transition-opacity">
            <h3 className="fw-black mb-0 tracking-tight-15 text-dark uppercase">
              MYLITTLESPORT
            </h3>
          </Link>

          <h1 className="h2 uppercase mb-2">
            Verify your email
          </h1>
          <p className="text-muted mb-5 font-medium">We have sent a 6-digit code to your email.</p>

          {(error || info) && (
            <div className={`alert ${error ? 'alert-danger' : 'alert-success'} small py-3 mb-4 text-center`} role="alert">
              {error ?? info}
            </div>
          )}

          <p className="fs-10 text-muted mb-4 uppercase tracking-widest font-bold">
            Sent to: <span className="text-dark">{email || 'your email'}</span>
          </p>

          <form className="d-flex flex-column gap-4" onSubmit={handleSubmit}>
            <div className="form-group">
              <input
                type="text"
                inputMode="numeric"
                pattern="\d*"
                maxLength={6}
                value={code}
                onChange={(e) => setCode(e.target.value.replace(/\D/g, '').slice(0, 6))}
                placeholder="000000"
                className="form-control-custom w-100 text-center fw-black fs-1 tracking-[0.5em] ps-[0.5em]"
                required
              />
            </div>

            <button
              type="submit"
              disabled={loading || !username}
              className={`btn-dark-custom w-100 py-3 ${
                loading ? 'opacity-70 cursor-not-allowed' : 'active:scale-95'
              }`}
            >
              {loading ? (
                <>
                  <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                  Verifying...
                </>
              ) : (
                'Verify account'
              )}
            </button>
          </form>

          <div className="mt-4">
            <button
              type="button"
              disabled={resending || !username}
              className="btn btn-link text-muted small fw-bold text-decoration-underline hover-dark"
              onClick={handleResend}
            >
              {resending ? 'Resending...' : 'Resend code'}
            </button>
          </div>

          <div className="mt-5 pt-5 border-top border-light text-center">
            <Link
              to="/register"
              className="text-muted small fw-bold text-uppercase tracking-widest transition-colors flex items-center justify-center gap-2 no-underline"
            >
              <span>←</span> Back to Registration
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default VerifyEmail

