import {
  type LoginRequest,
  type RegisterRequest,
  type RegisterResponse,
  type AuthResponse,
  type VerifyEmailRequest,
  type ResendVerificationRequest
} from '../../shared/types'

const API_URL = 'http://localhost:8080/api/auth'

export const authService = {

  register: async (userData: RegisterRequest): Promise<RegisterResponse> => {
    const response = await fetch(`${API_URL}/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    })

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || 'Error al registrar el usuario')
    }

    return await response.json() as RegisterResponse
  },

  verifyEmail: async (payload: VerifyEmailRequest): Promise<AuthResponse> => {
    const response = await fetch(`${API_URL}/verify-email`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || 'Invalid or expired verification code')
    }

    const authResponse = await response.json() as AuthResponse
    
    if (!authResponse.requiresVerification) {
      sessionStorage.setItem('token', authResponse.token)
      const { 
        id, userId, role, ROL, 
        emailVerified, requiresVerification, verificationExpiresInSeconds, 
        ...userWithoutSensitiveData 
      } = authResponse.user as any;
      sessionStorage.setItem('user', JSON.stringify(userWithoutSensitiveData))
    }

    return authResponse
  },

  resendVerificationCode: async (payload: ResendVerificationRequest): Promise<RegisterResponse> => {
    const response = await fetch(`${API_URL}/resend-verification-code`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || 'No se pudo reenviar el código')
    }

    return await response.json() as RegisterResponse
  },

  login: async (credentials: LoginRequest): Promise<AuthResponse> => {
    const response = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    })

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || 'Invalid username or password')
    }

    const authResponse = await response.json() as AuthResponse
    
    if (!authResponse.requiresVerification) {
      sessionStorage.setItem('token', authResponse.token)
      const { 
        id, userId, role, ROL, 
        emailVerified, requiresVerification, verificationExpiresInSeconds, 
        ...userWithoutSensitiveData 
      } = authResponse.user as any;
      sessionStorage.setItem('user', JSON.stringify(userWithoutSensitiveData))
    }

    return authResponse
  },

  logout: () => {
    sessionStorage.removeItem('user')
    sessionStorage.removeItem('token')
  }
}