import {
  type LoginRequest,
  type RegisterRequest,
  type RegisterResponse,
  type UserResponse,
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

  verifyEmail: async (payload: VerifyEmailRequest): Promise<UserResponse> => {
    const response = await fetch(`${API_URL}/verify-email`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}))
      throw new Error(errorData.message || 'Código de verificación inválido o expirado')
    }

    const authResponse = await response.json() as AuthResponse
    sessionStorage.setItem('token', authResponse.token)

    const user: UserResponse = {
      username: authResponse.user.username,
      role: authResponse.user.role,
      name: '', surnames: '', email: authResponse.user.email, profileImgUrl: '', birthday: new Date(), createAt: ''
    }
    sessionStorage.setItem('user', JSON.stringify(user))

    return user
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

  login: async (credentials: LoginRequest): Promise<UserResponse> => {
    const response = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    })

    if (!response.ok) {
      throw new Error('Credenciales incorrectas o error en el servidor')
    }

    const authResponse = await response.json() as AuthResponse
    sessionStorage.setItem('token', authResponse.token)
    
    const user: UserResponse = {
      username: authResponse.user.username,
      role: authResponse.user.role,
      name: '', surnames: '', email: authResponse.user.email, profileImgUrl: '', birthday: new Date(), createAt: ''
    }
    sessionStorage.setItem('user', JSON.stringify(user))

    return user
  },

  logout: () => {
    sessionStorage.removeItem('user')
    sessionStorage.removeItem('token')
  }
}